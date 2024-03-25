package com.yundingshuyuan.recruit.framework.starter.idempotent.core.token;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Strings;
import com.yundingshuyuan.recruit.framework.starter.idempotent.config.IdempotentProperties;
import com.yundingshuyuan.recruit.framework.starter.idempotent.core.AbstractIdempotentExecuteHandler;
import com.yundingshuyuan.recruit.framework.starter.idempotent.core.IdempotentParamWrapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.opengoofy.index12306.framework.starter.cache.DistributedCache;
import org.opengoofy.index12306.framework.starter.convention.errorcode.BaseErrorCode;
import org.opengoofy.index12306.framework.starter.convention.exception.ClientException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

/**
 * 基于 Token 验证请求幂等性, 通常应用于 RestAPI 方法
 */
@RequiredArgsConstructor
public final class IdempotentTokenByRestAPIExecuteHandler extends AbstractIdempotentExecuteHandler implements IdempotentTokenService {

    private final DistributedCache distributedCache;
    private final IdempotentProperties idempotentProperties;

    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_PREFIX_KEY = "idempotent:token:";
    private static final long TOKEN_EXPIRED_TIME = 6000;

    /**
     * 此方法用于构建一个 IdempotentParamWrapper 对象。
     *
     * @param joinPoint ProceedingJoinPoint 对象
     * @return 一个 IdempotentParamWrapper 对象
     */
    @Override
    protected IdempotentParamWrapper buildWrapper(ProceedingJoinPoint joinPoint) {
        return new IdempotentParamWrapper();
    }

    /**
     * 此方法用于创建一个唯一的 token。
     *
     * @return 一个唯一的 token
     */
    @Override
    public String createToken() {
        // 生成一个唯一的 token
        String token = Optional.ofNullable(Strings.emptyToNull(idempotentProperties.getPrefix())).orElse(TOKEN_PREFIX_KEY) + UUID.randomUUID();
        // 将 token 存储在分布式缓存中
        distributedCache.put(token, "", Optional.ofNullable(idempotentProperties.getTimeout()).orElse(TOKEN_EXPIRED_TIME));
        // 返回生成的 token
        return token;
    }

    /**
     * 此方法用于处理请求。
     * 它在缓存中检查 token 的存在。
     * 如果找不到 token，它会抛出一个异常。
     *
     * @param wrapper IdempotentParamWrapper 对象
     */
    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        // 从请求中获取 token
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader(TOKEN_KEY);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(TOKEN_KEY);
            // 如果 token 为空，抛出一个异常
            if (StrUtil.isBlank(token)) {
                throw new ClientException(BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR);
            }
        }
        // 在分布式缓存中删除 token
        Boolean tokenDelFlag = distributedCache.delete(token);
        // 如果删除失败（也就是说，这个 token 已经存在），抛出一个异常
        if (!tokenDelFlag) {
            String errMsg = StrUtil.isNotBlank(wrapper.getIdempotent().message())
                    ? wrapper.getIdempotent().message()
                    : BaseErrorCode.IDEMPOTENT_TOKEN_DELETE_ERROR.message();
            throw new ClientException(errMsg, BaseErrorCode.IDEMPOTENT_TOKEN_DELETE_ERROR);
        }
    }
}
package com.mc.starblog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mc.starblog.exception.BusinessException;
import com.mc.starblog.service.CustomUserDetails;
import com.mc.starblog.service.CustomUserDetailsService;
import com.mc.starblog.utils.JwtUtil;
import com.mc.starblog.utils.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private boolean isExcluded(String path) {
        return List.of(
                "/api/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/api-docs/**",
                "/docs/**"
        ).stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 跳过登录和注册接口的 Token 验证
        String path = request.getRequestURI();
        if (isExcluded(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, 401, "未提供Token");
            return;
        }

        String jwt = authHeader.substring(7);
        try {
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    sendErrorResponse(response, 401, "Token 已过期或无效");
                    logger.warn("Token 已过期或无效");
                    return;
                }
            }
        } catch (BusinessException e) {
            logger.error("JWT 验证失败", e);
            sendErrorResponse(response, e.getCode(), e.getMessage());
            return;
        } catch (Exception e) {
            logger.error("JWT 验证失败", e);
            sendErrorResponse(response, 401, "无效的 Token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    // 发送错误响应
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // 设置响应字符编码为 UTF-8
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(statusCode, message)));
    }
}
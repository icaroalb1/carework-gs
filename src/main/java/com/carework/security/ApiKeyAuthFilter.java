package com.carework.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-API-KEY";
    private final String apiKey;
    private final List<RequestMatcher> publicMatchers = List.of(
            new AntPathRequestMatcher("/api/auth/login"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/h2-console/**")
    );

    public ApiKeyAuthFilter(String apiKey) {
        this.apiKey = apiKey;
        // DEBUG: Print no console quando o filtro é criado
        System.out.println("🔐 === API KEY FILTER CONSTRUÍDO ===");
        System.out.println("🔐 Chave esperada: '" + apiKey + "'");
        System.out.println("🔐 Tamanho da chave: " + (apiKey != null ? apiKey.length() : "NULL"));
        System.out.println("🔐 =================================");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!request.getRequestURI().startsWith("/api")) {
            return true;
        }
        return publicMatchers.stream().anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String providedKey = request.getHeader(HEADER_NAME);
        
        // DEBUG: Print completo no response
        if (!Objects.equals(apiKey, providedKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("text/plain");
            
            String debugInfo = String.format(
                "❌ Invalid API Key\n\n" +
                "📤 CHAVE ENVIADA: '%s'\n" +
                "📥 CHAVE ESPERADA: '%s'\n\n" +
                "📏 Tamanho enviado: %d\n" +
                "📏 Tamanho esperado: %d\n" +
                "🔤 Header usado: %s\n" +
                "🌐 URL: %s\n" +
                "📝 Método: %s",
                providedKey,
                apiKey,
                providedKey != null ? providedKey.length() : 0,
                apiKey != null ? apiKey.length() : 0,
                HEADER_NAME,
                request.getRequestURL(),
                request.getMethod()
            );
            
            response.getWriter().write(debugInfo);
            
            // Também print no console
            System.out.println("🔐 === FALHA NA VALIDAÇÃO DA API KEY ===");
            System.out.println(debugInfo);
            System.out.println("🔐 =====================================");
            
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "api-key-client",
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_API")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
package com.yagizeris.springsecurityjwtexample.common.util.securityUtil;

import java.io.IOException;
import java.util.HashSet;

import com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.constants.Messages;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    /*
     * OnePerRequestFiler:
     *  Kullanıcı tarafından    yapılan her HTTP request'i için yalnızca bir defa filtreleme işlemi yapmamızı sağlayan sınıftır.
     * Her bir request için filtreme işleminin bir defa yapılması garantilenir. 
     * Bu sınıf extends edildiği sınıflara doFilterInternal adlı bir method override eder. 
     * Bu method filtreleme mantığını içermektedir ve her HTTP request için yalnızca bir kez çağrılır.
     * Bu sayede FilterChain içerisindeki diğer filtrelerin birden fazla kez çalışmasını engeller. 
     */

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull  FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader(Messages.JwtRequest.REQUEST_HEADER);
        final String jwt;
        final String username;

        /*Kullanıcıdan herhangi bir HTTP request geldiği zaman başlığında Authorization veya Bearer ifadesi bulunur.
         * Bu kısımda bunu kontrol ediyoruz. Eğer bu değerler bulunmaz ise FilterChain'e request ve response'ları vererek 
         * istemci tarafından gönderilen değerleri geri döndürüyoruz. 
        */
        if(header==null || !header.startsWith(Messages.JwtRequest.TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        // Eğer Bearer 7. index'den başlar ise biz JWT Token'imize ulaşabiliyoruz.
        jwt = header.substring(7);
        try{
            username = jwtService.findUsername(jwt);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if(jwtService.isTokenValid(jwt,userDetails)){
                    var authorities = new HashSet<GrantedAuthority>(userDetails.getAuthorities().size());
                    for(var role : userDetails.getAuthorities()){
                        authorities.add(new SimpleGrantedAuthority(Messages.JwtRequest.ROLE_PREFIX + role.toString()));
                    }
                /*Bu nesne kullanıcı tarafından gönderilen UserDetails parametresi alarak verilen bilgilere göre kullanıcı oluşturur.
                  Ayrıca getAuthorizes() method'u ile de kullanıcını yetkilerini belirlememizi sağlar.
                 */
                    UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                /*Kullanıcının bilglerinin, kullanıcının göndermiş olduğu request bilgisi üzerinden
                  güncellenmesi halinde en güncel halini authenticationToken nesnesinin içerisindeki
                  setDetails()  methodunu kullanarak güncellenmemizi sağlar.Yani kullanıcının token'i en
                  güncel token expiration date ve diğer kullanıcı bilgileri ile authenticationToken'ın içerisindeki
                  details kısmında tutulacak.
                */
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /*
                    Sonrasında ise Spring Security içerisindeki SecurityContext'in de en güncel halini
                    authenticationToken ile güncelliyoruz.
                */
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException exception){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(exception.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}

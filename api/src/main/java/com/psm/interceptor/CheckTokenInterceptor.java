package com.psm.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    // intercept user before who access restricted resource
    // return true: yes, can access
    // return false: no, not allowed to access

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        System.out.println(method);
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        System.out.println(token);
        if(token == null){
            ResultVO resultVO = new ResultVO(RespondStatus.LOGIN_FAIL_NOT, "Please login first!", null);
            doResponse(response,resultVO);
        }else{
            try {
                // user can access the restricted resource
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("Zhang"); //解析token的SigningKey必须和生成token时设置密码一致
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String id = body.getId();

                return true;
            }catch (ExpiredJwtException e){
                ResultVO resultVO = new ResultVO(RespondStatus.LOGIN_FAIL_OVERDUE, "Timeout, Please login again!", null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVO resultVO = new ResultVO(RespondStatus.LOGIN_FAIL_NOT, "Token is illegal!", null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVO resultVO = new ResultVO(RespondStatus.LOGIN_FAIL_NOT, "Please login first！", null);
                doResponse(response,resultVO);
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response,ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }


}

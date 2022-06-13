package com.project.finance.clientAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ClientRequestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String ERROR_MESSAGE = "Something went wrong while parsing /login request body";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(request.getParameter("login") + " login");
        System.out.println(request.getParameter("password") + "passwrod");
        String parsedReq;
        //try{
            /*StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            parsedReq = sb.toString();*/

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            //ClientLoginRequest authRequest = objectMapper.readValue(parsedReq, ClientLoginRequest.class);
            ClientLoginRequest authRequest = new ClientLoginRequest(login,password);
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(authRequest.login, authRequest.password);
            System.out.println(token.toString() + " token");
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);


        //}
        /*catch (IOException e){
            logger.error(ERROR_MESSAGE, e);
            throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
        }*/
    }


}

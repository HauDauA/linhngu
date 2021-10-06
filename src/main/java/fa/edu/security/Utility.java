package fa.edu.security;

import javax.servlet.http.HttpServletRequest;

public class Utility {
    public  static  String getSizeURL(HttpServletRequest request){
        String siteUrl=request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(),"");
    }
}

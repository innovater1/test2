package com.example.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/* RestConfig 클래스에서 진입점을 "/api"로 했기 때문에
@Path로 추가한 "/test"를 포함하게 되면 전체 요청 경로는 "/api/test"가 됨.
 */
@Path("/test")
public class RestAPIExample {
    // 동일한 요청에 대해 GET,POST 요청을 구분하여 동작.
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello API Service";
    }
    /* POST의 경우 쿼리 파라미터. 즉 HTML 폼이나 HTTP Body를 통해 전달 되는 name=value 형태의 파라미터를 읽어와
    사용하고 있으며 이외에도 /api/test/hello와 같은 형식의 파라미터도 사용할 수 있음.
     */
    @POST
    @Path("{msg}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@PathParam("msg") String msg) {
        return msg + "API Service";
    }
}

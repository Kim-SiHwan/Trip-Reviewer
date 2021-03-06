package kim.sihwan.trip_reviewer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = {"7. Home"})
@Controller
public class HomeController implements ErrorController {

    @ApiOperation(value = "Front-end 연결",notes = "루트 경로로 진입했을 때 front-end로 연결")
    @GetMapping("/")
    public String index() {
        return "/index.html";
    }

    @ApiOperation(value = "error->Front-end 연결",notes = "error로 리턴되었을 때 front-end로 연결")
    @GetMapping("/error")
    public String redirect() {
        return "/index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
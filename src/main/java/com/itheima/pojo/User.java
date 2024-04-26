package com.itheima.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;


import java.time.LocalDateTime;


//lombock自动生成setter,getter,tostring

@Data

public class User {
    @NotNull
    private Integer id;//主键ID

    private String username;//用户名

    @JsonIgnore//让springmvc把当前对象转化成json时忽略password
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱

    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

    private String sex;
}

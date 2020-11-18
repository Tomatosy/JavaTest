package com.seasky.sytest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tbuser")

public class UserInfo {
    @TableId(value = "UserID", type = IdType.AUTO)
    private Integer userId;
    @TableField("UserName")
    private String userName;
    @TableField("PassWord")
    private String passWord;
}
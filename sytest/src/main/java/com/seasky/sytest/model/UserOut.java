package com.seasky.sytest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserOut extends UserInfo {
    private String userDesc;
}

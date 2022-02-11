package org.joe.ocw.common.base.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 默认情况下JWT是未加密的，任何人都可以解读其内容，因此不要存敏感信息
 */
public class JwtInfo {
    private String id;
    private String nickname;
    private String avatar;
    //权限、角色等

}

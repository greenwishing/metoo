package com.metoo.dto.merchant;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantUser;
import com.metoo.core.domain.user.User;
import com.metoo.dto.StatefulDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.utils.JodaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/20
 */
public class MerchantUserDTO extends StatefulDTO {

    private MerchantDTO merchant = new MerchantDTO();
    private UserDTO user = new UserDTO();
    private String creationTime;

    public MerchantUserDTO() {
    }

    public MerchantUserDTO(MerchantUser merchantUser) {
        super(merchantUser);
        Merchant merchant = merchantUser.getMerchant();
        this.merchant = new MerchantDTO(merchant);
        User user = merchantUser.getUser();
        this.user = new UserDTO(user);
        this.creationTime = JodaUtils.dateTimeToString(merchantUser.getCreationTime());
    }

    public MerchantDTO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDTO merchant) {
        this.merchant = merchant;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public static List<MerchantUserDTO> toDTOs(List<MerchantUser> merchantUsers) {
        List<MerchantUserDTO> merchantUserDTOs = new ArrayList<>();
        for (MerchantUser merchantUser : merchantUsers) {
            MerchantUserDTO merchantUserDTO = new MerchantUserDTO(merchantUser);
            merchantUserDTOs.add(merchantUserDTO);
        }
        return merchantUserDTOs;
    }
}

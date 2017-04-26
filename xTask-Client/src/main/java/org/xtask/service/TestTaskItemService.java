package org.xtask.service;

import org.springframework.stereotype.Service;

/**
 * Created by zhxy on 17/4/26.
 */
@Service
public class TestTaskItemService {

    private Integer i= 0;
    public void runTask(){
        System.out.println("######################################################################".replace("#",i.toString()));
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        System.out.println("#########################  ###########################################");
        System.out.println("###################### sucess !#######################################");
        System.out.println("#########################   ##########################################");
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        System.out.println("");

        i++;

    }

}

package com.ppp_admin.ppp_order_app;

import java.util.ArrayList;

/**
 * Created by Admin on 6/26/2016.
 */
public class Order_Agent_Info {

    // agent id list
    ArrayList<String> Agent_id_list;
    // get agent id list from order table
    ArrayList<OrderTable> OT_ArrayList;

    // con
    Order_Agent_Info(ArrayList<String> agents, ArrayList<OrderTable> orders ){
        Agent_id_list = agents;
        OT_ArrayList = orders;
    }

    public ArrayList<String> getAgent_id_list() {
        return Agent_id_list;
    }

    public void setAgent_id_list(ArrayList<String> agent_id_list) {
        Agent_id_list = agent_id_list;
    }

    public ArrayList<OrderTable> getOT_ArrayList() {
        return OT_ArrayList;
    }

    public void setOT_ArrayList(ArrayList<OrderTable> OT_ArrayList) {
        this.OT_ArrayList = OT_ArrayList;
    }


}

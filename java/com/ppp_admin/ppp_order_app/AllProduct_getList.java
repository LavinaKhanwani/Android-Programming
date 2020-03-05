package com.ppp_admin.ppp_order_app;

import java.util.ArrayList;

/**
 * Created by Admin on 3/29/2016.
 */
public class AllProduct_getList {

    ArrayList<Product_View_Obj> list = new ArrayList<Product_View_Obj>();
    ArrayList<add_SingleItem_Product> list_singleItemProduct = new ArrayList<add_SingleItem_Product>();
    ArrayList<ItemWithoutColor_SingleItem> list_SIPWithOutColor = new ArrayList<>();
    ArrayList<Integer> subType_productArrayList = new ArrayList<Integer>();
    ArrayList<String> subType_productNameArrayList = new ArrayList<String>();

    /*==========================   Product Type 1 ===============================================*/
    /*********************************************************************************************
     * Name:- Product_View_Obj
     * Description:- creating getters and setters for String value ,boolean value and integer color
     * to set value and get values.
     **********************************************************************************************/
    public class Product_View_Obj {
        // declare string variable
        String info;

        // declare variable for boolean
        boolean cb_status;

        // declare variable for color as datatype int
        int color;

        // declare constructor
        Product_View_Obj(String s) {
            this.info = s;
            this.cb_status = false;
        }

        // creating getter and setter
        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isCb_status() {
            return cb_status;
        }

        public void setCb_status(boolean cb_status) {
            this.cb_status = cb_status;
        }
    }

    /*********************************************************************************************
     * Name:-  get_ISI_PipeList()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the ISI Pipe items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> get_ISI_PipeList() {

        // add product item in arraylist object as list
        list.add(new Product_View_Obj("LMS 16mm"));
        list.add(new Product_View_Obj("MMS 16mm"));
        list.add(new Product_View_Obj("HMS 16mm"));
        list.add(new Product_View_Obj("LMS 20mm"));
        list.add(new Product_View_Obj("MMS 20mm"));
        list.add(new Product_View_Obj("HMS 20mm"));
        list.add(new Product_View_Obj("LMS 25mm"));
        list.add(new Product_View_Obj("MMS 25mm"));
        list.add(new Product_View_Obj("HMS 25mm"));
        list.add(new Product_View_Obj("LMS 32mm"));
        list.add(new Product_View_Obj("MMS 32mm"));
        list.add(new Product_View_Obj("HMS 32mm"));
        list.add(new Product_View_Obj("LMS 40mm"));
        list.add(new Product_View_Obj("MMS 40mm"));
        list.add(new Product_View_Obj("HMS 40mm"));
        list.add(new Product_View_Obj("LMS 50mm"));
        list.add(new Product_View_Obj("MMS 50mm"));
        list.add(new Product_View_Obj("HMS 50mm"));
        list.add(new Product_View_Obj("19mm 1.2mm"));
        list.add(new Product_View_Obj("19mm 1.5mm"));
        list.add(new Product_View_Obj("19mm 2.0mm"));

        return list;
    }

    /*********************************************************************************************
     * Name:-  get_NON_ISI_PipeList()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the NON ISI Pipe items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> get_NON_ISI_PipeList() {

        // add product item in arraylist object as list
        list.add(new Product_View_Obj("Commercial 1.0 19mm"));
        list.add(new Product_View_Obj("Commercial 1.0 20mm"));
        list.add(new Product_View_Obj("Commercial 1.0 25mm"));
        list.add(new Product_View_Obj("Nano 1.2 19mm"));
        list.add(new Product_View_Obj("Nano 1.2 20mm"));
        list.add(new Product_View_Obj("Nano 1.2 25mm"));
        list.add(new Product_View_Obj("Nawab 1.5 19mm"));
        list.add(new Product_View_Obj("Nawab 1.5 20mm"));
        list.add(new Product_View_Obj("Nawab 1.5 25mm"));
        list.add(new Product_View_Obj("Badshah 2.0 19mm"));
        list.add(new Product_View_Obj("Badshah 2.0 20mm"));
        list.add(new Product_View_Obj("Badshah 2.0 25mm"));

        return list;
    }

    /*********************************************************************************************
     * Name:-  getCableTieslist()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the cable tie items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getCableTieslist() {

        // add product item in arraylist object as list
        list.add(new Product_View_Obj("100mm x 1.8mm"));
        list.add(new Product_View_Obj("60mm x 2.0mm"));
        list.add(new Product_View_Obj("100mm x 2.2mm"));
        list.add(new Product_View_Obj("150mm x 2.2mm"));
        list.add(new Product_View_Obj("75mm x 2.5mm"));
        list.add(new Product_View_Obj("100mm x 2.5mm"));
        list.add(new Product_View_Obj("150mm x 2.5mm"));
        list.add(new Product_View_Obj("200mm x 2.5mm"));
        list.add(new Product_View_Obj("300mm x 3.3mm"));
        list.add(new Product_View_Obj("150mm x 3.6mm"));
        list.add(new Product_View_Obj("200mm x 3.6mm"));
        list.add(new Product_View_Obj("300mm x 3.3mm"));
        list.add(new Product_View_Obj("150mm x 3.6mm"));
        list.add(new Product_View_Obj("200mm x 3.6mm"));
        list.add(new Product_View_Obj("300mm x 3.6mm"));
        list.add(new Product_View_Obj("250mm x 4.8mm"));
        list.add(new Product_View_Obj("250mm x 4.2mm"));
        list.add(new Product_View_Obj("300mm x 4.8mm"));
        list.add(new Product_View_Obj("364mm x 4.8mm"));
        list.add(new Product_View_Obj("200mm x 4.8mm"));
        list.add(new Product_View_Obj("450mm x 4.8mm"));
        list.add(new Product_View_Obj("200mm x 7.6mm"));
        list.add(new Product_View_Obj("300mm x 7.6mm"));
        list.add(new Product_View_Obj("364mm x 7.6mm"));
        list.add(new Product_View_Obj("430mm x 7.6mm"));
        list.add(new Product_View_Obj("530mm x 7.6mm"));
        list.add(new Product_View_Obj("630mm x 8.8mm"));
        list.add(new Product_View_Obj("710mm x 8.8mm"));

        return list;
    }
    /*********************************************************************************************
     * Name:-  getAccessoriesOfPipes()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the Accessories Pipes items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getISI_WALL_BEND_ACCESSORIES() {

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getISI_FABRICATED_ACCESSORIES() {

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        list.add(new Product_View_Obj("32 MM"));
        list.add(new Product_View_Obj("40 MM"));
        list.add(new Product_View_Obj("50 MM"));

        return list;
    }
    public ArrayList<Product_View_Obj> getISI_FABRICATED_NORMAL_ACCESSORIES(){

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }
    public ArrayList<Product_View_Obj> getISI_COUPLER_ACCESSORIES() {

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getISI_SADDLE_ACCESSORIES() {

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getISI_ELBOW_ACCESSORIES() {

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getISI_TEE_ACCESSORIES(){

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getISI_FAN_ACCESSORIES() {
        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getISI_EXTRA_ACCESSORIES(){

        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        return list;
    }

    /*********************************************************************************************
     * Name:-  getPVCJunctionBox()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the PVC junction box item to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getPVCJunctionBox() {

        list.add(new Product_View_Obj("1 WAY TERMINAL 20 MM"));
        list.add(new Product_View_Obj("1 WAY TERMINAL 25 MM"));
        list.add(new Product_View_Obj("2 WAY THROUGH 20 MM"));
        list.add(new Product_View_Obj("2 WAY THROUGH 25 MM"));
        list.add(new Product_View_Obj("2 WAY A-TYPE 20 MM"));
        list.add(new Product_View_Obj("2 WAY A-TYPE 25 MM"));
        list.add(new Product_View_Obj("3 WAY TEE 20 MM"));
        list.add(new Product_View_Obj("3 WAY TEE 25 MM"));
        list.add(new Product_View_Obj("4 WAY INTER-SECTION 20 MM"));
        list.add(new Product_View_Obj("4 WAY INTER-SECTION 25 MM"));

        return list;
    }

    /*********************************************************************************************
     * Name:-  getPVCDeepJunctionBox()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the PCV deep junction box items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getPVCDeepJunctionBox() {

        list.add(new Product_View_Obj("1 WAY TERMINAL 20 MM"));
        list.add(new Product_View_Obj("1 WAY TERMINAL 25 MM"));
        list.add(new Product_View_Obj("2 WAY THROUGH 20 MM"));
        list.add(new Product_View_Obj("2 WAY THROUGH 25 MM"));
        //list.add(new Product_View_Obj("2 WAY A-TYPE 20 MM"));
        list.add(new Product_View_Obj("2 WAY A-TYPE 25 MM"));
        list.add(new Product_View_Obj("3 WAY TEE 20 MM"));
        list.add(new Product_View_Obj("3 WAY TEE 25 MM"));
        list.add(new Product_View_Obj("4 WAY INTER-SECTION 20 MM"));
        list.add(new Product_View_Obj("4 WAY INTER-SECTION 25 MM"));

        return list;
    }

    /*********************************************************************************************
     * Name:-  getPVCNormalJuntionBox()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the PCV normal junction box items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getPVCNormalJuntionBox() {

        list.add(new Product_View_Obj("1 WAY TERMINAL 19 MM"));
        list.add(new Product_View_Obj("2 WAY THROUGH 19 MM"));
        list.add(new Product_View_Obj("2 WAY A-TYPE 19 MM"));
        list.add(new Product_View_Obj("3 WAY TEE 19 MM"));
        list.add(new Product_View_Obj("4 WAY INTER-SECTION 19 MM"));

        return list;
    }

    /*********************************************************************************************
     * Name:-  getLXPlates_White_CB()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the lx plates items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getLXPlates_White_CB() {

        list.add(new Product_View_Obj("1 MODULE 3.5 X 3.5"));
        list.add(new Product_View_Obj("2 MODULE 3.5 X 3.5"));
        list.add(new Product_View_Obj("1 MODULE 4.5 X 4.5"));
        list.add(new Product_View_Obj("2 MODULE 4.5 X 4.5"));
        list.add(new Product_View_Obj("3 MODULE 4.5 X 4.5"));
        list.add(new Product_View_Obj("3 MODULE-LONG 4.5 X 6.5"));
        list.add(new Product_View_Obj("4 MODULE 4.5 X 6.5"));
        list.add(new Product_View_Obj("5 MODULE 4.5 X 7.5"));
        list.add(new Product_View_Obj("6 MODULE 4.5 X 8.5"));
        list.add(new Product_View_Obj("7 MODULE 4.5 X 9.5"));
        list.add(new Product_View_Obj("8 MODULE-HORI 4.5 X 10.5"));
        list.add(new Product_View_Obj("9 MODULE 4.5 X 10.5"));
        list.add(new Product_View_Obj("10 MODULE 4.5 X 12.5"));
        list.add(new Product_View_Obj("12 MODULE-LONG 4.5 X 15.5"));
        list.add(new Product_View_Obj("8 MODULE-SQUARE 6.5 X 6.5"));
        list.add(new Product_View_Obj("12 MODULE-HORI 6.5 X 8.5"));
        list.add(new Product_View_Obj("12 MODULE-VERTI 6.5 X 8.5"));
        list.add(new Product_View_Obj("16 MODULE-HORI 6.5 X 10.5"));
        list.add(new Product_View_Obj("16 MODULE-VERTI 6.5 X 10.5"));
        list.add(new Product_View_Obj("20 MODULE-HORI 6.5 X 12.5"));
        list.add(new Product_View_Obj("20 MODULE-VERTI 6.5 X 12.5"));
        list.add(new Product_View_Obj("24 MODULE-VERTI 6.5 X 15.5"));
        list.add(new Product_View_Obj("24 MODULE-VERTI 6.5 X 15.5"));
        list.add(new Product_View_Obj("16 MODULE-LONG 7.5 X 10.5"));
        list.add(new Product_View_Obj("18 MODULE-HORI 8.5 X 10.5"));
        list.add(new Product_View_Obj("18 MODULE-VERTI 8.5 X 10.5"));
        list.add(new Product_View_Obj("24 MODULE-HORI 8.5 X 12.5"));
        list.add(new Product_View_Obj("24 MODULE-VERTI 8.5 X 12.5"));
        list.add(new Product_View_Obj("24 MODULE-HORI 9.5 X 12.5"));
        list.add(new Product_View_Obj("24 MODULE-VERTI 9.5 X 12.5"));
        list.add(new Product_View_Obj("24 MODULE-VERTI 10.5 X 12.5"));
        list.add(new Product_View_Obj("24 MODULE-HORI 10.5 X 12.5"));
        list.add(new Product_View_Obj("32 MODULE-VERTI 12.5 X 12.5"));
        list.add(new Product_View_Obj("32 MODULE-HORI 12.5 X 12.5"));

        return list;
    }

    /*********************************************************************************************
     * Name:-  getLXPlates()
     * Output :- ArrayList of Product_View_Obj
     * Description :- Add all the lx plates items to the list and return the same
     *********************************************************************************************/
    public ArrayList<Product_View_Obj> getLXPlates() {

        list.add(new Product_View_Obj("1 WAY 3.5 X 3.5"));
        list.add(new Product_View_Obj("2 WAY 3.5 X 3.5"));
        list.add(new Product_View_Obj("1 WAY 4.5 X 4.5"));
        list.add(new Product_View_Obj("2 WAY 4.5 X 4.5"));
        list.add(new Product_View_Obj("3 WAY 4.5 X 4.5"));
        list.add(new Product_View_Obj("3 WAY-LONG 4.5 X 6.5"));
        list.add(new Product_View_Obj("4 WAY 4.5 X 6.5"));
        list.add(new Product_View_Obj("5 WAY 4.5 X 7.5"));
        list.add(new Product_View_Obj("6 WAY 4.5 X 8.5"));
        list.add(new Product_View_Obj("7 WAY 4.5 X 9.5"));
        list.add(new Product_View_Obj("8 WAY-HORI 4.5 X 10.5"));
        list.add(new Product_View_Obj("9 WAY 4.5 X 10.5"));
        list.add(new Product_View_Obj("10 WAY 4.5 X 12.5"));
        list.add(new Product_View_Obj("12 WAY-LONG 4.5 X 15.5"));
        list.add(new Product_View_Obj("8 WAY-SQUARE 6.5 X 6.5"));
        list.add(new Product_View_Obj("12 WAY-HORI 6.5 X 8.5"));
        list.add(new Product_View_Obj("12 WAY-VERTI 6.5 X 8.5"));
        list.add(new Product_View_Obj("16 WAY-HORI 6.5 X 10.5"));
        list.add(new Product_View_Obj("16 WAY-VERTI 6.5 X 10.5"));
        list.add(new Product_View_Obj("20 WAY-HORI 6.5 X 12.5"));
        list.add(new Product_View_Obj("20 WAY-VERTI 6.5 X 12.5"));
        list.add(new Product_View_Obj("24 WAY-HORI 6.5 X 15.5"));
        list.add(new Product_View_Obj("24 WAY-VERTI 6.5 X 15.5"));
        list.add(new Product_View_Obj("16 WAY-LONG 7.5 X 10.5"));
        list.add(new Product_View_Obj("18 WAY-HORI 8.5 X 10.5"));
        list.add(new Product_View_Obj("18 WAY-VERTI 8.5 X 10.5"));

        list.add(new Product_View_Obj("24 WAY-HORI 8.5 X 12.5"));
        list.add(new Product_View_Obj("24 WAY-VERTI 8.5 X 12.5"));
        list.add(new Product_View_Obj("24 WAY-HORI 9.5 X 12.5"));
        list.add(new Product_View_Obj("24 WAY-VERTI 9.5 X 12.5"));
        list.add(new Product_View_Obj("24 WAY-HORI 10.5 X 12.5"));
        list.add(new Product_View_Obj("24 WAY-VERTI 10.5 X 12.5"));

        list.add(new Product_View_Obj("32 WAY-VERTI 12.5 X 12.5"));
        list.add(new Product_View_Obj("32 WAY-HORI 12.5 X 12.5"));

        return list;
    }

    public ArrayList<Product_View_Obj> getNon_ISI_Junction_Deep_Box__Accessories() {

        list.add(new Product_View_Obj("1 WAY 19 MM"));
        list.add(new Product_View_Obj("1 WAY 20 MM"));
        list.add(new Product_View_Obj("1 WAY 25 MM"));
        list.add(new Product_View_Obj("1 WAY 32 MM"));
        list.add(new Product_View_Obj("1 WAY 40 MM"));
        list.add(new Product_View_Obj("1 WAY 50 MM"));

        list.add(new Product_View_Obj("2 WAY 19 MM"));
        list.add(new Product_View_Obj("2 WAY 20 MM"));
        list.add(new Product_View_Obj("2 WAY 25 MM"));
        list.add(new Product_View_Obj("2 WAY 32 MM"));
        list.add(new Product_View_Obj("2 WAY 40 MM"));
        list.add(new Product_View_Obj("2 WAY 50 MM"));

        list.add(new Product_View_Obj("3 WAY 19 MM"));
        list.add(new Product_View_Obj("3 WAY 20 MM"));
        list.add(new Product_View_Obj("3 WAY 25 MM"));
        list.add(new Product_View_Obj("3 WAY 32 MM"));
        list.add(new Product_View_Obj("3 WAY 40 MM"));
        list.add(new Product_View_Obj("3 WAY 50 MM"));

        list.add(new Product_View_Obj("4 WAY 19 MM"));
        list.add(new Product_View_Obj("4 WAY 20 MM"));
        list.add(new Product_View_Obj("4 WAY 25 MM"));
        list.add(new Product_View_Obj("4 WAY 32 MM"));
        list.add(new Product_View_Obj("4 WAY 40 MM"));
        list.add(new Product_View_Obj("4 WAY 50 MM"));

        return list;
    }

    public ArrayList<Product_View_Obj> getNon_ISI_Deep_Box__Accessories() {

        list.add(new Product_View_Obj("1 WAY 19 MM"));
        list.add(new Product_View_Obj("1 WAY 20 MM"));
        list.add(new Product_View_Obj("1 WAY 25 MM"));

        list.add(new Product_View_Obj("2 WAY 19 MM"));
        list.add(new Product_View_Obj("2 WAY 20 MM"));
        list.add(new Product_View_Obj("2 WAY 25 MM"));

        list.add(new Product_View_Obj("3 WAY 19 MM"));
        list.add(new Product_View_Obj("3 WAY 20 MM"));
        list.add(new Product_View_Obj("3 WAY 25 MM"));

        list.add(new Product_View_Obj("4 WAY 19 MM"));
        list.add(new Product_View_Obj("4 WAY 20 MM"));
        list.add(new Product_View_Obj("4 WAY 25 MM"));

        return list;
    }

    public ArrayList<Product_View_Obj> getNon_ISI_Other_Accessories() {

        list.add(new Product_View_Obj("19 MM"));
        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));

        return list;
    }

    public ArrayList<Product_View_Obj> getNon_ISI_Other_Accessories_Of_PCCB() {

        list.add(new Product_View_Obj("19 MM"));
        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));
        list.add(new Product_View_Obj("32 MM"));
        list.add(new Product_View_Obj("40 MM"));
        list.add(new Product_View_Obj("50 MM"));

        return list;
    }

    // Flex Box
    public ArrayList<Product_View_Obj> getOrange_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));

        return list;
    }

    public ArrayList<Product_View_Obj> getQueen_FB() {

        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getCharm_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));
        list.add(new Product_View_Obj("3X9"));

        return list;
    }

    public ArrayList<Product_View_Obj> getSuperClassic_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));

        return list;
    }

    public ArrayList<Product_View_Obj> getKing_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));
        list.add(new Product_View_Obj("3X9"));

        return list;
    }

    public ArrayList<Product_View_Obj> getBadshah_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));

        return list;
    }

    public ArrayList<Product_View_Obj> getOrange_FB_WithOut_Handle() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));

        return list;
    }

    public ArrayList<Product_View_Obj> getApple_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));
        list.add(new Product_View_Obj("3X9"));

        return list;
    }

    public ArrayList<Product_View_Obj> getBoss_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));

        return list;
    }

    public ArrayList<Product_View_Obj> getJewel_FB() {

        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));

        return list;
    }

    public ArrayList<Product_View_Obj> getClassic_FB() {
        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));

        return list;
    }

    public ArrayList<Product_View_Obj> getAP_FB() {
        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));

        return list;
    }

    public ArrayList<Product_View_Obj> getDeluex_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getRoyal_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getKing_FB_AMP() {
        list.add(new Product_View_Obj("15 AMP"));
        return list;
    }


    public ArrayList<Product_View_Obj> getNano_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getBlackberry_FB_WithOut_and_With_Handle() {
        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        list.add(new Product_View_Obj("3X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getInnova_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getGalaxy_FB() {
        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X8"));
        list.add(new Product_View_Obj("3X4"));
        return list;
    }

    public ArrayList<Product_View_Obj> getRangoli_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getWhite_Cirtus_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getTejas_FB() {
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getPower_Spike_Guard_ITEM() {

        list.add(new Product_View_Obj("1.5 MTRS"));
        list.add(new Product_View_Obj("3 MTRS"));
        list.add(new Product_View_Obj("5 MTRS"));
        list.add(new Product_View_Obj("10 MTRS"));

        return list;
    }

    public ArrayList<Product_View_Obj> getPivot_PG(){
        list.add(new Product_View_Obj("1.6 MTRS"));
        return list;
    }

    public ArrayList<Product_View_Obj> getElectra_SG(){

        list.add(new Product_View_Obj("1 LAYER 3 WAY  + 2 USB 1.6 MTRS"));
        list.add(new Product_View_Obj("2 LAYER 8 WAY  + 4 USB 1.6 MTRS"));
        return list;
    }

    public ArrayList<Product_View_Obj> getDiya(){
        list.add(new Product_View_Obj("2X5"));
        return list;
    }

    public ArrayList<Product_View_Obj> getGrey_FB(){
        list.add(new Product_View_Obj("2X5"));
        list.add(new Product_View_Obj("2X10"));
        return  list;
    }

    public ArrayList<Product_View_Obj> getDelta_FB(){
        list.add(new Product_View_Obj("2X5 YARD"));

        return list;
    }

    public ArrayList<Product_View_Obj> get_New_Nano_FB(){

        list.add(new Product_View_Obj("2x4"));
        list.add(new Product_View_Obj("2x8"));
        list.add(new Product_View_Obj("3x4"));
        return list;
    }

    public  ArrayList<Product_View_Obj> getPlastic_Sheet(){

        list.add(new Product_View_Obj("4.5 X 4.5"));
        list.add(new Product_View_Obj("4.5 X 6.5"));
        list.add(new Product_View_Obj("4.5 X 7.5"));
        list.add(new Product_View_Obj("4.5 X 8.5"));
        list.add(new Product_View_Obj("4.5 X 9.5"));
        list.add(new Product_View_Obj("4.5 X 10.5"));
        list.add(new Product_View_Obj("4.5 X 11.5"));
        list.add(new Product_View_Obj("4.5 X 12.5"));
        list.add(new Product_View_Obj("4.5 X 15.5"));
        list.add(new Product_View_Obj("4.5 X 13.5"));

        list.add(new Product_View_Obj("4.5 X 14.5"));
        list.add(new Product_View_Obj("6.5 X 6.5"));
        list.add(new Product_View_Obj("6.5 X 8.5"));
        list.add(new Product_View_Obj("6.5 X 10.5"));
        list.add(new Product_View_Obj("6.5 X 12.5"));

        list.add(new Product_View_Obj("8.5 X 10.5"));
        list.add(new Product_View_Obj("8.5 X 12.5"));
        list.add(new Product_View_Obj("10.5 X 10.5"));
        list.add(new Product_View_Obj("10.5 X 12.5"));
        list.add(new Product_View_Obj("12.5 X 12.5"));

        return list;
    }

    public ArrayList<Product_View_Obj> getRoyal_Plates(){

        list.add(new Product_View_Obj("3.5 X 3.5"));
        list.add(new Product_View_Obj("4.5 X 4.5"));
        list.add(new Product_View_Obj("4.5 X 6.5"));
        list.add(new Product_View_Obj("4.5 X 7.5"));
        list.add(new Product_View_Obj("4.5 X 8.5"));
        list.add(new Product_View_Obj("4.5 X 9.5"));
        list.add(new Product_View_Obj("4.5 X 10.5"));
        list.add(new Product_View_Obj("4.5 X 12.5"));
        list.add(new Product_View_Obj("4.5 X 15.5"));
        list.add(new Product_View_Obj("6.5 X 6.5"));

        list.add(new Product_View_Obj("6.5 X 8.5"));
        list.add(new Product_View_Obj("6.5 X 10.5"));
        list.add(new Product_View_Obj("6.5 X 12.5"));
        list.add(new Product_View_Obj("6.5 X 15.5"));
        list.add(new Product_View_Obj("7.5 X 10.5"));
        list.add(new Product_View_Obj("8.5 X 10.5"));
        list.add(new Product_View_Obj("8.5 X 12.5"));
        list.add(new Product_View_Obj("10.5 X 12.5"));

        return  list;
    }

    public ArrayList<Product_View_Obj> getNON_ISI_Galaxy_Lock(){

        list.add(new Product_View_Obj("19 MM"));
        list.add(new Product_View_Obj("20 MM"));
        list.add(new Product_View_Obj("25 MM"));

        return list;
    }

    public ArrayList<Product_View_Obj> getPVCWireElectrical_and_FRLS(){

        list.add(new Product_View_Obj("45 MTRS;0.75 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;0.75 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;0.75 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;1.00 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;1.00 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;1.00 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;1.50 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;1.50 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;1.50 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;2.00 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;2.00 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;2.00 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;2.50 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;2.50 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;2.50 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;4.00 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;4.00 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;4.00 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;6.00 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;6.00 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;6.00 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;10.00 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;10.00 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;10.00 SQ. MM"));

        list.add(new Product_View_Obj("45 MTRS;16.00 SQ. MM"));
        list.add(new Product_View_Obj("90 MTRS;16.00 SQ. MM"));
        list.add(new Product_View_Obj("180 MTRS;16.00 SQ. MM"));

        return list;
    }

    public ArrayList<Product_View_Obj> getPVC_GOLD_WIRE(){

        list.add(new Product_View_Obj("1.00 SQ. MM"));
        list.add(new Product_View_Obj("1.50 SQ. MM"));
        list.add(new Product_View_Obj("2.50 SQ. MM"));
        list.add(new Product_View_Obj("4.00 SQ. MM"));
        list.add(new Product_View_Obj("6.00 SQ. MM"));
        return list;
    }

    public ArrayList<Product_View_Obj> getCable_Tray(){

        list.add(new Product_View_Obj("15 X 15"));
        list.add(new Product_View_Obj("25 X 15"));
        list.add(new Product_View_Obj("25 X 25"));
        list.add(new Product_View_Obj("30 X 25"));
        list.add(new Product_View_Obj("40 X 30"));
        list.add(new Product_View_Obj("45 X 25"));
        list.add(new Product_View_Obj("45 X 30"));
        list.add(new Product_View_Obj("45 X 45"));
        list.add(new Product_View_Obj("45 X 60"));
        list.add(new Product_View_Obj("60 X 25"));
        list.add(new Product_View_Obj("60 X 40"));
        list.add(new Product_View_Obj("60 X 45"));
        list.add(new Product_View_Obj("60 X 60"));
        list.add(new Product_View_Obj("75 X 25"));
        list.add(new Product_View_Obj("75 X 45"));
        list.add(new Product_View_Obj("75 X 60"));
        list.add(new Product_View_Obj("75 X 75"));
        list.add(new Product_View_Obj("100 X 75"));
        list.add(new Product_View_Obj("100 X 100"));

        return list;
    }

      public ArrayList<Product_View_Obj> getEVA_Edge_list(){

          list.add(new Product_View_Obj("1 MODULE"));
          list.add(new Product_View_Obj("2 MODULE"));
          list.add(new Product_View_Obj("3 MODULE"));
          list.add(new Product_View_Obj("4 MODULE"));
          list.add(new Product_View_Obj("6 MODULE"));
          list.add(new Product_View_Obj("8-H MODULE"));
          list.add(new Product_View_Obj("8-SQ MODULE"));
          list.add(new Product_View_Obj("12 MODULE"));
          list.add(new Product_View_Obj("16 MODULE"));
          list.add(new Product_View_Obj("18 MODULE"));

        return list;
    }
    /*================================= Product Type 2 =========================================*/

    /*********************************************************************************************
     * Name:- add_SingleItem_Product
     * Description:- creating getters and setters for String value ,boolean value
     * to set value and get values.
     **********************************************************************************************/
    public class add_SingleItem_Product {
        // create int object to store image
        private int single_itemProduct;
        // create boolean value for checkbox
        private boolean checkBox_staus;
        // create string object to store prouct name
        private String singleItemProductName;

        // declare constructor
        add_SingleItem_Product(int imageProduct, String textview_productname) {
            this.single_itemProduct = imageProduct;
            this.singleItemProductName = textview_productname;
            this.checkBox_staus = false;
        }

        /* create all getter and setter */
        public int getSingle_itemProduct() {
            return single_itemProduct;
        }

        public void setSingle_itemProduct(int single_itemProduct) {
            this.single_itemProduct = single_itemProduct;
        }

        public boolean isCheckBox_staus() {
            return checkBox_staus;
        }

        public void setCheckBox_staus(boolean checkBox_staus) {
            this.checkBox_staus = checkBox_staus;
        }

        public String getSingleItemProductName() {
            return singleItemProductName;
        }

        public void setSingleItemProductName(String singleItemProductName) {
            this.singleItemProductName = singleItemProductName;
        }
    }

    /*********************************************************************************************
     * Name:-  getList_TejasModular()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the Tejas Modular Gang Box items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getList_TejasModular() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas1way, "1 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas2way, "2 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas3way, "3 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas4way, "4 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas4way, "5 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas6way, "6 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas8waysqr, "8 Way Square"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas8wayhor, "8 Way Hor"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas9way, "9 Way Hor"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas10way, "10 Way HORI"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas10way, "12 Way Square"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas16way, "16 Way"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas16way, "18 Way"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getMultiPlug_list()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the Multiplugs items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getMultiPlug_list() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_301, "INTERNATIONAL 5A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_301, "INTERNATIONAL 13A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_301, "INTERNATIONAL 15A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_304, "BADSHAH 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_305, "BOSS 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_306, "NAWAB 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_307, "2 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_308, "3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_309, "2 PIN CONVERSION PLUG(WITH INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_310, "3 PIN CONVERSION PLUG 5A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_310, "3 PIN CONVERSION PLUG 5A TO 15A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_318, "SPLENDOR 2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_318, "SPLENDOR MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_320, "NAWAB 2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_320, "NAWAB MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_322, "BADSHAH 2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_322, "BADSHAH MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_327, "CHARM 3 PIN CONVERSION"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_328, "NANO 3 PIN CONVERSION 5A TO 15A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_329, "CHARM 2 PIN CONVERSION"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_330, "BADSHAH 2 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_331, "NANO 2 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_332, "NANO 2 PIN MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_324, "ZEL 2 PIN TOP(UREA BODY)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_333, "UNO 2 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_334, "UNO 2 PIN MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_335, "UNO 2 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_335, "UNO 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_337, "NANI 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_340, "CLASSIC 2 PIN TO 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_341, "DIYA 2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.multiplug_341, "DIYA 2 PIN MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1210, "DIYA 2 PIN CONVERSION"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1211,"DIYA 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1212,"DIYA 3 PIN 16A"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_multiplug,"EVA 3 PIN 15A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pf_1138,"TEJAS 2 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1139,"TEJAS 3 PIN"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getRound_Plate()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the Round Plate items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getRound_Plate(){

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_456,"DIYA 3.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_442,"BADSHAH 3.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_443,"NAWAB 3.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_447,"SPLENDOR 3.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_457,"DIYA 4.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_441,"BADSHAH 4.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_445,"SPLENDOR 4.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_446,"BOSS 4 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_440,"SPLENDOR 5.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_444,"SPLENDOR 6 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_448,"BADSHAH 6.5 INCHES(FAN-PLATE)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_439,"SPLENDOR 7.5 INCHES(FAN-PLATE)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_453,"MINT 3.75 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_454,"MINT 4.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_455,"MINT 7 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_473,"TEJAS-PLAIN 3.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_474,"TEJAS-2-HOLE-PLATE 3.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_475,"TEJAS-2-HOLE-PLATE 4.5 INCHES"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_round_plate6_5,"EVA 6.5 INCHES(WITH-COVER-PLATE)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_round_plate7_5,"EVA 7.5 INCHES(WITH-COVER-PLATE)"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getOtherElectrical_Accessories()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the other electrical items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getOtherElectrical_Accessories(){

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.strip_connector,"STRIP CONNECTOR 5A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.strip_connector,"STRIP CONNECTOR 10A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.strip_connector,"STRIP CONNECTOR 15A"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.dummy_plates,"M.C.B DUMMY PLATE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.dummy_plates,"MODULAR DUMMY PLATE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.dummy_plates,"SWITCH DUMMY PLATE"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.starter_holder,"STARTER HOLDER"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.rotor_holder,"CHARMS UREA ROTOR HOLDER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.rotor_holder,"MODULAR DUMMY PLATE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.rotor_holder,"ROTOR HOLDER NANO(P.C)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.safety_plug,"SAFETY PLUG 5A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.safety_plug,"SAFETY PLUG 15A"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.backelite_connector,"BACKELITE STRIP CONNECTOR 5A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.backelite_connector,"BACKELITE STRIP CONNECTOR 10A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.backelite_connector,"BACKELITE STRIP CONNECTOR 15A"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.backelite_connector,"BACKELITE STRIP CONNECTOR 30A"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.life_long,"LIFE LONG KETTLE IRON CONNECTOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.life_long,"LIFE LONG IRON CONNECTOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.boss_deluxe,"BOSS IRON CONNECTOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.boss_deluxe,"DELUXE IRON CONNECTOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.zel,"ZEL IRON CONNECTOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.zel,"ZEL KETTLE IRON CONNECTOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.payal,"PAYAL IRON CONNECTOR(WITH INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.nawab_pt,"NAWAB IRON CONNECTOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.nawab_pt,"NAWAB KETTLE IRON CONNECTOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.badshah_pt,"BADSHAH IRON CONNECTOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.badshah_pt,"BADSHAH KETTLE IRON CONNECTOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.mazboot,"MAZBOOT IRON CONNECTOR(SOLID BRASS PARTS)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.splendor,"SPLENDOR IRON CONNECTOR(WITH EARTHING)"));

        /*Raval plug */
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.wall_plug,"RAVAL PLUG WP NO 6"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.wall_plug,"RAVAL PLUG WP NO 8"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.wall_plug,"RAVAL PLUG WP NO 10 X 38"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.wall_plug,"RAVAL PLUG WP NO 10 X 50"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.wall_plug,"RAVAL PLUG WP NO 12 X 63"));

        return  list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getPattiStand()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the patti stand items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getPattiStand(){

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.t_5a,"T-5"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.t_5b,"T-10"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getBulkHead()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the bulhead items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getBulkHead(){

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1045,"3 PIN TELE PHONE SET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.badshah,"BADSHAH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.badhshah_foot_light,"BADSHAH FOOT LIGHT WITH BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.metal_patti_stand,"METAL PATTI STAND"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.badshah_patti_stand,"BADSHAH PATTI STAND"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_756,"BOSS LED FOOT LIGHT"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.classic,"CLASSIC"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_759,"FOOT LIGHT PLATE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1041,"J BOX FOR TELE PHONE AND ANTENNA SOCKET(SOCKET TYPE)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.nawab,"NAWAB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.nawab_foot_light,"NAWAB FOOT LIGHT WITH BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.nawab_patti_stand,"NAWAB PATTI STAND"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.queen_patti_stand,"QUEEN PATTI STAND"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.plus_patti_stand,"PLUS PATTI STAND"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1019,"ROSETTE BOX 1 LINE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1019,"ROSETTE BOX 1 LINE DUPLEX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1019,"ROSETTE BOX 2 LINE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1019,"ROSETTE BOX 2 LINE DUPLEX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1043,"TELE PHONE SOCKET 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1043,"TELE PHONE TOP 3 PIN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.pt_1043,"ROSETTE BOX 1 LINE DUPLEX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.vazir,"VAZIR"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getGoldSwitch()
     * Output :- ArrayList of Add_SingleItem_Product_SingleItem
     * Description :- Add all the gold switch items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getGoldSwitch() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_101, "6A BOSS 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_102, "6A BOSS 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_103, "6A BOSS BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_105, "6A CHERRY 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_106, "6A CHERRY 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_107, "6A CHERRY BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_109, "6A DELUXE 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_110, "6A DELUXE 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_111, "6A DELUXE BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_113, "6A NANI 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_114, "6A NANI 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_115, "6A NANI BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_132, "16A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_132, "16A 1 WAY SWITCH(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_133, "16A 2 WAY SWITCH"));

        /*  change images */
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_117, "BED SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_118, "BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_119, "2 WAY BED SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_120, "SURFACE SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_135, "SURFACE BELL-PUSH SWITCH"));


        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_117, "6A 3 PIN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_118, "6A 2 IN 1 SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_119, "6A 5 PIN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_120, "6A INTERNATIONAL SOCKET"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_121, "2 PIN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_136, "16A UNIVERSAL INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_123, "16A UNIVERSAL INDIAN SOCKET(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_137, "16A UNIVERSAL SOCKET(SAFETY-SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_137, "16A UNIVERSAL SOCKET(SAFETY-SHUTTER & J.BOX)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_139, "3 IN 1 INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_141, "3 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_140, "3 IN 1 INDIAN SOCKET(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_142, "3 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER & J.BOX)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_143, "5 IN 1 INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_144, "5 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_145, "5 IN 1 INDIAN SOCKET(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_146, "5 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER & J.BOX)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.switch_32_dp, "32A.DP ROYAL SURFACE SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.switch_32_dp, "32A.DP GLORY SURFACE SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.switch_32_dp, "32A.DP GOLD CONCEALED SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.switch_32_dp, "32A.DP TEJAS SURFACE SWITCH"));


        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_122, "TV SWITCH ANTENNA"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_123, "TV SOCKET ANTENNA"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_124, "2 LINE TEL.JACK"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_108, "CHERRY INDICATOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_116, "NANI INDICATOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_126, "FAN REGULATOR SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_127, "FAN REGULATOR SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_128, "FAN REGULATOR SWITCH 4-STEP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_129, "FAN REGULATOR SOCKET 5-STEP"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_130, "KIT-KAT 10A FUSE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_131, "KIT-KAT 16A FUSE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.gold_2_pin, "2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.gold_malefemale, "2 PIN MALE-FEMALE"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getGlorySwitch()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the glory switch items to the list and return the same
     *********************************************************************************************/
      public ArrayList<add_SingleItem_Product> getGlorySwitch() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_101, "6A LX 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_102, "6A LX 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_103, "6A LX BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_105, "6A CX 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_106, "6A CX 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_107, "6A CX BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_109, "6A GX 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_110, "6A GX 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_111, "6A GX BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_113, "6A SX 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_114, "6A SX 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_115, "6A SX BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_132, "16A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_133, "16A 1 WAY SWITCH(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_134, "16A 2 WAY SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_117, "6A 3 PIN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_118, "6A 2 IN 1 SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_119, "6A 5 PIN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_120, "6A INTERNATIONAL SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_135, "16A INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_136, "16A INDIAN SOCKET(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_137, "16A UNIVERSAL SOCKET(SAFETY-SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_138, "16A UNIVERSAL SOCKET(SAFETY-SHUTTER & J.BOX)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_121, "2 PIN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_163, "GX 2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_164, "GX 2 PIN TOP MALE-FEMALE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_176, "TEJAS 2 PIN TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_177, "TEJAS 2 PIN TOP MALE-FEMALE"));
        // list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_150,"FLASH HOLDER GLORY SWITCH TYPE"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_122, "TV SWITCH ANTENNA"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_123, "TV SOCKET ANTENNA"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_124, "2 LINE TEL.JACK"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_108, "CX INDICATOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_116, "SX INDICATOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_126, "FAN REGULATOR SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_127, "FAN REGULATOR SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_128, "FAN REGULATOR SWITCH 4-STEP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_129, "FAN REGULATOR SOCKET 5-STEP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_175, "DIYA SURFACE FAN REGULATOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_130, "10A KIT-KAT FUSE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_131, "16A KIT-KAT FUSE"));
        //list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.ev_104,"MOTOR STARTER WITH SOCKET & LED DP"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_139, "3 IN 1 INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_141, "3 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_140, "3 IN 1 INDIAN SOCKET(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_142, "3 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER & J.BOX)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_143, "5 IN 1 INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_145, "5 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_144, "5 IN 1 INDIAN SOCKET(J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_146, "5 IN 1 UNIVERSAL SOCKET(SAFETY-SHUTTER & J.BOX)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "LX 6A S.S COMBINE(J.BOX)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "8 IN 1 16A INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "8 IN 1 16A INDIAN SOC. WITH J BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "8 IN 1 16A INDIAN SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "8 IN 1 16A INDIAN SOC. WITH J BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "8 IN 1 16A INDIAN INT'L SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.glory_162, "8 IN 1 16A INDIAN SOC. WITH J BOX"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getOneSwitch()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the one switch items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getOneSwitch() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_001, "PEARL 6A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_002, "PEARL 6A 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_003, "PEARL 6A BELL PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_004, "PEARL 6A 1 WAY SWITCH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_005, "PEARL 6A BELL PUSH SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_tejas_100,"TEJAS 6A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_101, "TEJAS 6A 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_102, "TEJAS 6A BELL PUSH SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_006, "NANO 6A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_007, "NANO 6A 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_008, "NANO 6A BELL PUSH SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_009, "EXCEL 16A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_010, "EXCEL 16A 1 WAY SWITCH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_011, "EXCEL 16A 2 WAY SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_diya_103, "DIYA 6A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_104, "DIYA 6A 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_105, "DIYA 6A BELL PUSH SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_012, "XUV 16A 2M 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_013, "XUV 16A 2M 1 WAY SWITCH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_014, "XUV 16A 2M 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_015, "XUV 6A 2M BELL PUSH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_016, "XUV 32A-DP 2M SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_017, "6A 2 PIN SOCKET ROUND"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_018, "6A 2 PIN SOCKET INTERNATIONAL"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_019, "6A 3 PIN SOCKET(ISI)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_020, "6A 2 IN 1 SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_021, "6A UNIVERSAL SOCKET"));
        // list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_121,"USB SOCKET"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_022, "6A MULTI SOCKET(CELL PHONE)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_023, "13A INTERNATIONAL SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_024, "16A UNIVERSAL SOCKET(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_025, "16A INDIAN SOCKET(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_025, "25A UNIVERSAL SOCKET(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_026, "6A S.S COMBINE(MOBILE PIN CHARGER)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_027, "FAN REGULATOR SWITCH 300W"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_028, "FAN REGULATOR SOCKET 450W"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_029, "E.M.E FAN REGULATOR 4-STEP 80W"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_030, "E.M.E FAN REGULATOR 5-STEP 80W"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_031, "WIRE OUTLET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_032, "TV SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_033, "BELL INDICATOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_034, "ELECTRONIC BUZZER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_034, "BULBUL BUZZER"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_036, "1M BLANK PLATE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_037, "2M BLANK PLATE"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_038, "NEON INDICATOR RED"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_039, "NEON INDICATOR NATURAL"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_040, "NEON INDICATOR GREEN"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_041, "ELECTRONIC NIGHT LAMP LED XUV"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_042, "TELEPHONE JACK 2 LINE(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_043, "TELEPHONE JACK 4 LINE(SHUTTER)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_044, "CAT 5 MODEM JACK(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_045, "CAT 6 MODEM JACK(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_046, "RJ-45 MODEM JACK"));
        //  list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_099,"ONE KIT-KAT FUSE"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_049, "PEARL MOTOR STARTER 20A SINGLE PHASE SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_050, "PEARL MOTOR STARTER 25A SINGLE PHASE SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_051, "MCB SINGLE POLE 6A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_051a, "MCB SINGLE POLE 10A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_051b, "MCB SINGLE POLE 16A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_051c, "MCB SINGLE POLE 20A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_051d, "MCB SINGLE POLE 25A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_051e, "MCB SINGLE POLE 32A SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_052, "MCB DOUBLE POLE 16A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_052a, "MCB DOUBLE POLE 20A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_052b, "MCB DOUBLE POLE 25A SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_052c, "MCB DOUBLE POLE 32A SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_097, "LX AC BOX SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_ev_105, "EVA MCB PROTECTED SOCKET(ISI)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_106, "FLASH HOLDER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_107, "FLASH HOLDER SOCKET TYPE"));

        //list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_053,"EXCEL 18 LED FOOT LIGHT[4M]"));
        //list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_054,"PEARL 18 LED FOOT LIGHT[4M]"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_055, "ROOM SERVICE SYSTEM FULL SET(PLATE+SWITCH+KEYTAG)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_056, "ROOM SERVICE SYSTEM 32A DP SWITCH(KEY TAG)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.one_057, "KEY TAG(KEY RING)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_110,"16A 1 IN 1 SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_110,"16A 1 IN 1 SWITCH WITH J BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_114,"16A 1 IN 1 SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_114,"16A 1 IN 1 SOCKET WITH J BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_112,"16A 3 IN 1 COMBINED"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_112,"16A 3 IN 1 COMBINED WITH J BOX"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_116,"16A 5 IN 1 COMBINED"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.on_116,"16A 5 IN 1 COMBINED WITH J BOX"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getPlug()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the  plug items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getPlug() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_44, "GOLD 6A 3 PIN-TOP(U)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_45, "GOLD 16A 3 PIN-TOP(U)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_154, "GX 6A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_155, "GX 16A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_158, "LX 6A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_160, "LX 16A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_159, "SX 6A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_161, "SX 16A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_171, "GALAXY 6A 3 PIN-TOP(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_172, "GALAXY 16A 3 PIN-TOP(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_312, "BADSHAH 6A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_313, "BADSHAH 16A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_314, "NAWAB 6A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_315, "NAWAB 16A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_338, "BLACKBERRY 6A 3 PIN-TOP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.angle_batten_339, "BLACKBERRY 16A 3 PIN-TOP"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getEvaSwitch()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the eva switches items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getEvaSwitch() {

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_1, "6A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_2, "6A 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_3, "6A BELL-PUSH SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_4, "6A BELL-PUSH SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_087, "6A 1 WAY SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_9, "16A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_10, "16A 2 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_11, "16A 1 WAY SWITCH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_67, "25A 1 WAY SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_68, "25A 1 WAY SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_6, "6A 1 WAY 2M BROAD SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_69, "6A 1 WAY BROAD SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_7, "6A 2 WAY 2M BROAD SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_70, "6A BELL-PUSH 2M BROAD SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_8, "6A BELL-PUSH 2M BROAD SWITCH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_13, "16A 1 WAY BROAD SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_7, "16A 2 WAY BROAD SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_14, "16A 1 WAY BROAD SWITCH(INDICATOR)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_016, "25A 1 WAY BROAD SWITCH"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_71, "25A 2 WAY BROAD SWITCH"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_72, "25A 1 WAY BROAD SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_61, "20A MOTOR STARTER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_62, "25A MOTOR STARTER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_63, "32A MOTOR STARTER"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_19, "6A 2 PIN SOCKET(SAFETY SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_20, "6A 2 IN 1 SOCKET(SAFETY SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_20, "6A 2 IN 1 SOCKET"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_21, "6A MULTI 2 IN 1 SOCKET(SAFETY SHUTTER)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_22, "6/13A INTERNATIONAL SOCKET(SAFETY SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_23, "6/16A UNIVERSAL SOCKET(SAFETY SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_24, "6/25A UNIVERSAL SOCKET(SAFETY SHUTTER)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_25, "VINTAGE MINI LIGHT DIMMER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_26, "VINTAGE MEDIUM LIGHT DIMMER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_27, "VINTAGE 4-STEP FAN REGULATOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_28, "VINTAGE 5-STEP FAN REGULATOR"));
        // change images
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_54, "VISTA MINI LIGHT DIMMER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_55 , "VISTA MEDIUM LIGHT DIMMER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_56, "VISTA 4-STEP FAN REGULATOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_57, "VISTA 5-STEP FAN REGULATOR"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_29, "RJ 11 SINGLE TEL.JACK(SHUTTER)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_30, "RJ 11 DOUBLE TEL.JACK"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_37, "MINI T.V SOCKET"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_35, "CABLE OUTLET"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_31, "RJ 45 COMPUTER JACK CAT-5"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_32, "RJ 45 COMPUTER JACK CAT-6"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_38, "BELL INDICATOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_33, "KIT-KAT FUSE 10A"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_32, "INDICATOR LAMP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_36, "BLANK PLATE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_39, "ELECTRONIC BUZZER"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_40, "3 LED DOWN LIGHT"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_41, "LED NIGHT LAMP"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_16, "32A D.P SWITCH(INDICATOR)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_42, "32A D.P SWITCH(KEY TAG)"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_43, "KEY TAG(RING)"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_44, "6A SINGLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_45, "10A SINGLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_46, "16A SINGLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_47, "20A SINGLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_48, "25A SINGLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_49, "32A SINGLE POLE MCB"));

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_50, "6A DOUBLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_51, "10A DOUBLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_52, "16A DOUBLE POLE MCB"));

        // change image
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_64, "20A DOUBLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_65, "25A DOUBLE POLE MCB"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.eva_66, "32A DOUBLE POLE MCB"));

        return list_singleItemProduct;
    }

    /*********************************************************************************************
     * Name:-  getTejas_Conceal_Box()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the tejas conceal box items to the list and return the same
     *********************************************************************************************/
    public ArrayList<add_SingleItem_Product> getTejas_Conceal_Box(){

        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_1or_2_way,"1 OR 2 WAY"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_3_way,"3 WAY"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_4_way,"4 WAY"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_6_way,"6 WAY"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_8_way,"8 WAY SQUARE"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_8_way,"8 WAY HOR"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_12_way,"12 WAY"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_16_way,"16 WAY"));
        list_singleItemProduct.add(new add_SingleItem_Product(R.drawable.tejas_18_way,"18 WAY"));

        return list_singleItemProduct;
    }

    /* ================================= Product Type 3 ====================================== */

    /*********************************************************************************************
     * Name:- ItemWithoutColor_SingleItem
     * Description:- creating getters and setters for String to set type of tester ,checkbox status,
     * and ImageView to set image.
     **********************************************************************************************/
    public class ItemWithoutColor_SingleItem {

        // create int object to store image
        private int single_itemProduct;
        // create boolean value for checkbox
        private boolean checkBox_staus;
        // create string object to store product name
        private String singleItemProductName;

        // declare constructor
        ItemWithoutColor_SingleItem(int imageProduct, String textview_productname) {
            this.single_itemProduct = imageProduct;
            this.singleItemProductName = textview_productname;
            this.checkBox_staus = false;
        }

        /* create all getter and setter */
        public int getSingle_itemProduct() {
            return single_itemProduct;
        }

        public void setSingle_itemProduct(int single_itemProduct) {
            this.single_itemProduct = single_itemProduct;
        }

        public boolean isCheckBox_staus() {
            return checkBox_staus;
        }

        public void setCheckBox_staus(boolean checkBox_staus) {
            this.checkBox_staus = checkBox_staus;
        }

        public String getSingleItemProductName() {
            return singleItemProductName;
        }

        public void setSingleItemProductName(String singleItemProductName) {
            this.singleItemProductName = singleItemProductName;
        }
    }

    /*********************************************************************************************
     * Name:-  getTester()
     * Output :- ArrayList of ItemWithoutColor_SingleItem
     * Description :- Add all the Tester items to the list and return the same
     *********************************************************************************************/
    public ArrayList<ItemWithoutColor_SingleItem> getTester() {

        // add product item in arraylist object as list
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.greeno, "Greeno"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sathiya, "Sathiya White"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sathiya, "Sathiya Green"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sathigold, "Sathi Gold Green"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sathigold, "Sathi Gold White"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sathideluxe, "Sathi Deluxe"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.splendortester, "Splendor"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.oxetester, "Oxe"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.chotutester, "Chotu"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charmstester, "Charms"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.glorytester, "Glory"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sumotester, "Sumo"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.oneindustrial, "One Industrial"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.zenindustrial, "Zen Industrial"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.badshahindustrial, "Badshah Industrial"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.kingindustrial, "King Industrial"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.diya, "Diya"));

        return list_SIPWithOutColor;
    }

    /*********************************************************************************************
     * Name:-  getHolder()
     * Output :- ArrayList of ItemWithoutColor_SingleItem
     * Description :- Add all the holder items to the list and return the same
     *********************************************************************************************/
    public ArrayList<ItemWithoutColor_SingleItem> getHolder() {

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_601a, "ANGLE JEWEL PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_601b, "ANGLE JEWEL METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angleu_metal_39, "ANGLE(U) METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_147, "ANGLE-GX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_147a, "ANGLE-GX PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_149, "ANGLE-LX METAL RING"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_149, "ANGLE-LX PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_156, "ANGLE-SX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_251a, "ANGLE ROYAL WHITE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_251b, "ANGLE ROYAL T.WOOD"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_251c, "ANGLE ROYAL METALIC"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_173, "CFL ANGLE HOLDER"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_176, "DIYA TWIN ANGLE BATTEN"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1131, "ANGLE CHARM"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1131, "BATTEN CHARM"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1133, "ANGLE PEARL"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1133, "BATTEN PEARL"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_608a, "BATTEN JEWEL PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_608b, "BATTEN JEWEL METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_40, "BATTEN(U) METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_148, "BATTEN-GX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_148a, "BATTEN-GX PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_150, "BATTEN-LX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_150a, "BATTEN-LX PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_157, "BATTEN-SX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_252a, "BATTEN ROYAL"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_252b, "BATTEN ROYAL T.WOOD"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_252c, "BATTEN ROYAL METALIC"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_629a, "JUMBO JEWEL PLASTIC RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_151, "JUMBO-LX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_148b, "JUMBO-GX METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_174, "CFL BATTEN HOLDER"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1129,"TEJAS ANGLE;RED"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1130,"TEJAS BATTEN;BLACK"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1129,"TEJAS ANGLE;RED"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1130,"TEJAS BATTEN;BLACK"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_616, "CEILING ROSE JEWEL(PARTS 2)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_616, "CEILING ROSE JEWEL(PARTS 3)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_42, "CEILING ROSE UREA(PARTS 3)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_152, "CEILING ROSE GX(PARTS 3)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_166, "CEILING ROSE LX(PARTS 3)"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_253a, "CEILING ROSE ROYAL(PARTS 3)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_253b, "CEILING ROSE ROYAL T.WOOD(PARTS 3)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_253c, "CEILING ROSE ROYAL METALIC(PARTS 3)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_615a, "JUMBO CEILING ROSE JEWEL"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_167, "JUMBO CEILING ROSE LX"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_630, "JEWEL METAL RING(SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_622, "JEWEL PLASTIC RING(SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_165, "METAL RING(SKIRT)"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1145,"PENDENT OBR;BLACK"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_bateten_165a, "PLASTIC RING(SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_1135, "GALAXY PENDENT HOLDER(WIRE & 2-PIN)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_169, "GALAXY METAL RING(SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_169a, "GALAXY PLASTIC RING(SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_631, "JEWEL METAL RING(WITHOUT-SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_623, "JEWEL PLASTIC RING(WITHOUT-SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_168, "METAL RING(WITHOUT-SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_168a, "LX PLASTIC RING(WITHOUT-SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_170, "GALAXY METAL RING(WITHOUT-SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_170a, "GALAXY PLASTIC RING(WITHOUT-SKIRT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.angle_batten_624, "NAWAB"));

        // GANPATI LIST HOLDERS
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_629_b,"JUMBO BATTEN(PC) METAL RING"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_615_b,"JUMBO CEILING ROSE(THREE PART)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_622b,"PC PENDENT(WITH SKIRT);BLACK"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_623b,"PC PENDENT(O.R);BLACK"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_627,"ZEL ADAPTOR(FULL BRASS)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_630b,"JEWEL PENDENT(WITH SKIRT);BLACK"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_631b,"JEWEL PENDENT(O.R);BLACK"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_620,"BABY BATTEN(PC)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_632,"JEWEL BABY BATTEN(BRASS RING PC)"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_633,"2 PIN PARALLEL ADAPTOR"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_634,"B.C. PARALLEL ADAPTOR"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_635,"MULTI PENDENT"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_449,"2 SLOT PROCELAIN"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_450,"3 SLOT PROCELAIN"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_450,"ROUND 2 SLOT PROCELAIN"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"C.F.L HOLDER"));

        // new arrivals
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.eva_edge_angle_holder1,"EDGE 5X5 ANGLE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.eva_edge_batten_plus_holder1,"EDGE 5X5 BATTEN"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.eva_edge_plus_angle_holder2,"EDGE PLUS 5X5 ANGLE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.eva_edge_batten_plus_holder1,"EDGE PLUS 5X5 BATTEN"));

        // changes images
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"EVA 5X5 ANGLE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"EVA 5X5 BATTEN"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"EVA 5X5 CEILING ROSE"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"UNI ANGLE HOLDER"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"UNI BATTEN HOLDER"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"UNI CEILING ROSE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pt_628,"UNI PENDANT"));

        return list_SIPWithOutColor;
    }

    public ArrayList<ItemWithoutColor_SingleItem> getPlastic_Board() {

        /* SWITCH BOARD */
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_400,"SWITCH BOARD 3 X 3"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_401,"SWITCH BOARD 4 X 4"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_402,"SWITCH BOARD 4 X 7"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_403,"SWITCH BOARD 6 X 6"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_404,"SWITCH BOARD 6 X 8"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_405,"SWITCH BOARD 8 X 10"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_406,"SWITCH BOARD 10 X 12"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_407,"SWITCH BOARD 12 X 12"));

        /* Horizontal board */
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_408,"HORI. BOARD 3.5 X 6"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_409,"HORI. BOARD 3.5 X 8"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_410,"HORI. BOARD 3.5 X 10"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_411,"HORI. BOARD 3.5 X 12"));

        /* Cutting Board */
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_401_a,"CUTTING BOARD 4 X 4(1G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_401_b,"CUTTING BOARD 4 X 4(2G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_408_a,"CUTTING BOARD 3.5 X 6(3G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_408_b,"CUTTING BOARD 3.5 X 6(4G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_409_a,"CUTTING BOARD 3.5 X 8(5G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_410_a,"CUTTING BOARD 3.5 X 10(6G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_410_b,"CUTTING BOARD 3.5 X 10(7G BAD)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_411_a,"CUTTING BOARD 3.5 X 12(8G BAD)"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_404_a,"CUTTING BOARD 6 X 8 X 2(7 WAY HORI)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_404_b,"CUTTING BOARD 6 X 8 X 2(8 WAY VERT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_405_a,"CUTTING BOARD 8 X 10 X 2(8 WAY HORI)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_405_b,"CUTTING BOARD 8 X 10(9 WAY VERT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_406_a,"CUTTING BOARD 10 X 12(12 WAY VERT)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_404_b,"CUTTING BOARD 10 X 12(12 WAY HORI)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pf_404_c,"CUTTING BOARD 10 X 12(14 WAY DP CUTTING)"));

        /* Single Board */
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.no_product_image_small,"SINGLE BOARD 4 X 4"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.singleboard4x7,"SINGLE BOARD 4 X 7"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.cameramountingbox,"4X4 CAMERA MOUNTING BOX"));

        return list_SIPWithOutColor;
    }

    /*********************************************************************************************
     * Name :- getDoorBell
     * Output :- Arraylist of add_SingleItem_Product
     * Description :- Add all the Door bell items to the list and return the same
     ********************************************************************************************/
    public ArrayList<ItemWithoutColor_SingleItem> getDoorBell() {

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.continuitymandirmantra, "Mandir Continuity Mantras"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.continuitykalashmantra, "Kalash Continuity Mantras"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.continuityroyalmantra, "Royal Continuity Mantras"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pridebell, "Pride Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pridebell, "Pride Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pridebell, "Pride Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.zelbell, "Zel Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.zelbell, "Zel Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.zelbell, "Zel Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.rahulbell, "Rahul Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.rahulbell, "Rahul Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.rahulbell, "Rahul Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.bossbell, "Boss Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.bossbell, "Boss Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.bossbell, "Boss Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.chotubell, "Chotu Bell(With Light Indicator) Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.chotubell, "Chotu Bell(With Light Indicator) Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.chotubell, "Chotu Bell(With Light Indicator) Electronic Ding Dong"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.addzel, "Add Zel Vocal Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charmsbell, "Charm Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charmsbell, "Charm Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charmsbell, "Charm Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sweetymusicalbell, "Sweety Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.splendormusicalbell, "Splendor Bell(With Light Indicator) Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.glorybell, "Glory Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.glorybell, "Glory Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.badshahparrotbell, "Badshah Parrot Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.badshahmusicalbell, "Badshah Musical Bell KDK+POD(2 in 1)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.badshahmusicalbell, "Badshah Musical Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nawabparrotbell, "Nawab Parrot Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.vazirmusicalbell, "Vazir Musical Bell(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.vazirparrotbell, "Vazir Parrot Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.splendordingdongbell, "Splendor Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.crystaldingdongbell, "Crystal Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.bossdingdongbell, "Boss Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.badshahdingdongbell, "Badshah Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.glorydingdongbell, "Glory Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sb03stereodingdongbell, "SB-03 Stereo Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.sb05stereodingdongbell, "SB-05 Stereo Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.industrialbell, "3' Industrial Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.industrialbell, "4' Industrial Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.industrialbell, "6' Industrial Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.industrialbell, "9' Industrial Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.industrialbell, "12' Industrial Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.splendorgongbell, "Splendor Gong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.fishgongbell, "Fish Gong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.indicatorbell, "Indicator Bell(For Multiple ways Rs.120/- Extra)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.bigindicatorbell, "Big Indicator Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.badshahbuzzer, "Badshah Buzzer"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.mousebuzzer, "Mouse Buzzer"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.zelbuzzer, "Zel Buzzer"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.rahulbuzzer, "Rahul Buzzer"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.bossbuzzer, "Boss Buzzer"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charms3in1, "Charm 3 in 1 MB-01 Church Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charms3in1, "Charm 3 in 1 MB-02 Polyphonic Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.charms3in1, "Charm 3 in 1 MB-03 Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nanobell, "Nano Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nanobell, "Nano Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nanobell, "Nano Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.pridebell2in1, "Pride Bell 2 in 1"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nanibell, "Nani Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nanibell, "Nani Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nanibell, "Nani Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.classicmusicalbell, "Classic Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.golddingdongbell, "Gold Ding Dong Bell"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.cutebell, "Cute Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.cutebell, "Cute Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.cutebell, "Cute Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.kakabell, "Kaka Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.kakabell, "Kaka Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.kakabell, "Kaka Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.mintbell, "Mint Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.mintbell, "Mint Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.mintbell, "Mint Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.inorbitbell, "Inorbit Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.inorbitbell, "Inorbit Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.inorbitbell, "Inorbit Bell Parrot"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nawabbell, "Nawab Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.nawabbell, "Nawab Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.galaxybell, "Galaxy Bell Vocal"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.galaxybell, "Galaxy Bell Musical(Hin/Eng)"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.galaxybell, "Galaxy Bell Parrot"));
        // new product added
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.door_bell_echo,"ECHO"));

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.doorl_bell_chorus_ding_dong,"Chrous Ding-Dong"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.door_bell_chorus_musical_bell,"Chrous Musical"));
        //list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.door_bell_chorus_musical_bell,"Chours Talking"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.door_bell_chorus_musical_bell,"Chrous Parrot Bell"));

        return list_SIPWithOutColor;
    }

    /*********************************************************************************************
     * Name:-  getPalazzo_Omyah_list()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the palazzo items to the list and return the same
     *********************************************************************************************/
    public ArrayList<ItemWithoutColor_SingleItem> getPalazzo_list(){

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"1 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"2 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"3 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"4 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"6 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"8 MODULE HORI"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"8 MODULE SQU"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"12 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"16 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.palazzo_1,"18 MODULE"));

        return list_SIPWithOutColor;
    }

    /*********************************************************************************************
     * Name:-  getPalazzo_Omyah_list()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the omyah items to the list and return the same
     *********************************************************************************************/
    public ArrayList<ItemWithoutColor_SingleItem> getOmyah_list(){

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"1 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"2 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"3 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"4 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"6 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"8 MODULE HORI"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"8 MODULE SQU"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"12 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"16 MODULE"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.omya_1,"18 MODULE"));

        return list_SIPWithOutColor;
    }

    /*********************************************************************************************
     * Name:-  getUNI_Gang_Box()
     * Output :- ArrayList of add_SingleItem_Product
     * Description :- Add all the uni gang box items to the list and return the same
     *********************************************************************************************/
    public ArrayList<ItemWithoutColor_SingleItem> getUNI_Gang_Box(){

        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_1way,"1 WAY"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_2way,"2 WAY"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_3way,"3 WAY"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_4way,"4 WAY"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_6way,"6 WAY"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_8way_sq,"8 WAY SQU"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_8way_h,"8 WAY HORI"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_12way,"12 WAY SQU"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_16way,"16 WAY"));
        list_SIPWithOutColor.add(new ItemWithoutColor_SingleItem(R.drawable.uni_18way,"18 WAY"));

        return list_SIPWithOutColor;
    }


    /**************************************** product sub type page *************************************************/

    /*********************************************************************************************
     * Name:-  getPVC_Conduit_Images()
     * Output :- ArrayList of Integer
     * Description :- Add all the junction box images to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getPVC_Conduit_Images() {

        subType_productArrayList.add(R.drawable.pt_501s);
        subType_productArrayList.add(R.drawable.pt_509s);
        subType_productArrayList.add(R.drawable.pt_501s);
        // OTHER ACCESSORIES
        subType_productArrayList.add(R.drawable.pt_508s);
        subType_productArrayList.add(R.drawable.pt_508s);
        subType_productArrayList.add(R.drawable.pt_508s);

        subType_productArrayList.add(R.drawable.coupling);
        subType_productArrayList.add(R.drawable.pt_505s);
        subType_productArrayList.add(R.drawable.pt_502s);
        subType_productArrayList.add(R.drawable.pt_503s);
        subType_productArrayList.add(R.drawable.pt_511s);
        // new product
        subType_productArrayList.add(R.drawable.pt_508s);

        return subType_productArrayList;
    }

    /*********************************************************************************************
     * Name:-  getPVC_Conduit_ProductName()
     * Output :- ArrayList of String
     * Description :- Add all the junction box product name to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getPVC_Conduit_ProductName() {

        subType_productNameArrayList.add("JUNCTION BOX");
        subType_productNameArrayList.add("DEEP JUNCTION BOX");
        subType_productNameArrayList.add("NORMAL JUNCTION BOX");
        subType_productNameArrayList.add("WALL BEND");
        subType_productNameArrayList.add("FABRICATED BEND");
        subType_productNameArrayList.add("FABRICATED BEND NAORMAL");
        subType_productNameArrayList.add("COUPLER");
        subType_productNameArrayList.add("SADDLE BASE WITH SCREW");
        subType_productNameArrayList.add("ELBOW");
        subType_productNameArrayList.add("TEE");
        subType_productNameArrayList.add("FAN BOX");
        subType_productNameArrayList.add("EXTRA LID WITH SREWS FOR J.BOX");
        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getLXRangeCoverPlate_Images()
     * Output :- ArrayList of Integer
     * Description :- Add all the lx cover plate image to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getLXRangeCoverPlate_Images() {

        subType_productArrayList.add(R.drawable.natural_cover_small);
        subType_productArrayList.add(R.drawable.metallic_cover_small);
        subType_productArrayList.add(R.drawable.black_cover_small);
        subType_productArrayList.add(R.drawable.wooden_cover_small);

        // base plates
        subType_productArrayList.add(R.drawable.snow_white_base);
        subType_productArrayList.add(R.drawable.silver_touch_base);
        subType_productArrayList.add(R.drawable.silky_black_base);

        return subType_productArrayList;
    }

    /*********************************************************************************************
     * Name:-  getLXRangeCoverPlate_ProductName()
     * Output :- ArrayList of String
     * Description :- Add all the lx cover plate names to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getLXRangeCoverPlate_ProductName() {

        subType_productNameArrayList.add("NATURAL");
        subType_productNameArrayList.add("METALLIC");
        subType_productNameArrayList.add("BLACK");
        subType_productNameArrayList.add("WOODEN");

        // base plate
        subType_productNameArrayList.add("NATURAL");
        subType_productNameArrayList.add("METALLIC");
        subType_productNameArrayList.add("BLACK");

        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getConcealBox_Image()
     * Output :- ArrayList of Integer
     * Description :- Add all the conceal image to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getConcealBox_Image() {

        subType_productArrayList.add(R.drawable.conceal_box);
        subType_productArrayList.add(R.drawable.conceal_box);
        subType_productArrayList.add(R.drawable.conceal_box);
        subType_productArrayList.add(R.drawable.conceal_box);

        return subType_productArrayList;
    }

    /*********************************************************************************************
     * Name:-  getConcealBoxName()
     * Output :- ArrayList of String
     * Description :- Add all the conceal box names to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getConcealBoxName() {
        subType_productNameArrayList.add("CONCEAL MODULAR BOX");
        subType_productNameArrayList.add("METAL BOX");
        subType_productNameArrayList.add("SSURFACE BOX");
        subType_productNameArrayList.add("CONCEAL BOARD");

        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getNON_ISI_Accessories_Name()
     * Output :- ArrayList of String
     * Description :- Add all the non isi accessories names to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getNON_ISI_Accessories_Name() {

        subType_productNameArrayList.add("JUNCTION BOX");
        subType_productNameArrayList.add("DEEP JUNCTION BOX");
        subType_productNameArrayList.add("ELBOW");
        subType_productNameArrayList.add("TEE");
        subType_productNameArrayList.add("COUPLING");
        subType_productNameArrayList.add("PATTI SADDLE");
        subType_productNameArrayList.add("PUSH SADDLE");
        subType_productNameArrayList.add("CLIP");
        subType_productNameArrayList.add("BEND");
        subType_productNameArrayList.add("EXTENSION RING");
        subType_productNameArrayList.add("FAN BOX");
        subType_productNameArrayList.add("FAN BOX(WITH HOOK)");
        subType_productNameArrayList.add("GALAXY LOCK SADDLE");

        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getNON_ISI_Accessories_Image()
     * Output :- ArrayList of Integer
     * Description :- Add all the non isi accessories image to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getNON_ISI_Accessories_Image() {

        subType_productArrayList.add(R.drawable.pt_501s);
        subType_productArrayList.add(R.drawable.pt_509s);
        // other accessories
        subType_productArrayList.add(R.drawable.pt_502s);
        subType_productArrayList.add(R.drawable.pt_503s);
        subType_productArrayList.add(R.drawable.coupling);
        subType_productArrayList.add(R.drawable.pt_505s);
        subType_productArrayList.add(R.drawable.pt_506s);
        subType_productArrayList.add(R.drawable.pt_519s);
        subType_productArrayList.add(R.drawable.pt_508s);
        subType_productArrayList.add(R.drawable.pt_510s);
        subType_productArrayList.add(R.drawable.pt_511s);
        subType_productArrayList.add(R.drawable.pt_512s);
        subType_productArrayList.add(R.drawable.pt_260s);

        return subType_productArrayList;
    }

    /*********************************************************************************************
     * Name:-  getFlex_Box_Name()
     * Output :- ArrayList of String
     * Description :- Add all the flex box names to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getFlex_Box_Name() {

        subType_productNameArrayList.add("ORANGE FLEX BOX");
        subType_productNameArrayList.add("QUEEN FLEX BOX");
        subType_productNameArrayList.add("CHARM FLEX BOX");
        subType_productNameArrayList.add("SUPER CLASSIC FLEX BOX");
        subType_productNameArrayList.add("KING FLEX BOX");
        subType_productNameArrayList.add("BADSHAH FLEX BOX");
        subType_productNameArrayList.add("ORANGE FLEX BOX(WITHOUT HANDLE)");
        subType_productNameArrayList.add("APPLE FLEX BOX(WITHOUT HANDLE)");
        subType_productNameArrayList.add("BOSS FLEX BOX");
        subType_productNameArrayList.add("JEWEL FLEX BOX");
        subType_productNameArrayList.add("CLASSIC FLEX BOX");
        subType_productNameArrayList.add("A.P(ALL PURPOSE)");
        subType_productNameArrayList.add("DELUXE FLEX BOX");
        subType_productNameArrayList.add("ROYAL FLEX BOX");
        subType_productNameArrayList.add("KING FLEX BOX");
        subType_productNameArrayList.add("BLACKBERRY FLEX BOX(WITH HANDLE)");
        subType_productNameArrayList.add("NANO FLEX BOX");
        subType_productNameArrayList.add("BLACKBERRY FLEX BOX(WITHOUT HANDLE)");
        subType_productNameArrayList.add("INNOVA FLEX BOX");
        subType_productNameArrayList.add("RANGOLI FLEX BOX");
        subType_productNameArrayList.add("GALAXY FLEX BOX");
        subType_productNameArrayList.add("WHITE CIRTUS FLEX BOX");
        subType_productNameArrayList.add("TEJAS FLEX BOX");
        subType_productNameArrayList.add("DIYA");
        subType_productNameArrayList.add("GREY");

        // new arrivals
        subType_productNameArrayList.add("DELTA");


        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getFlex_Box_Images()
     * Output :- ArrayList of Integer
     * Description :- Add all the flex box image to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getFlex_Box_Images() {

        subType_productArrayList.add(R.drawable.flexbox_202);
        subType_productArrayList.add(R.drawable.flexbox_204);
        subType_productArrayList.add(R.drawable.flexbox_206);
        subType_productArrayList.add(R.drawable.flexbox_207);
        subType_productArrayList.add(R.drawable.flexbox_208);
        subType_productArrayList.add(R.drawable.flexbox_209);
        subType_productArrayList.add(R.drawable.flexbox_211);
        subType_productArrayList.add(R.drawable.flexbox_214);
        subType_productArrayList.add(R.drawable.flexbox_203);
        subType_productArrayList.add(R.drawable.flexbox_212);
        subType_productArrayList.add(R.drawable.flexbox_215);
        subType_productArrayList.add(R.drawable.flexbox_205);
        subType_productArrayList.add(R.drawable.flexbox_216);
        subType_productArrayList.add(R.drawable.flexbox_217);
        subType_productArrayList.add(R.drawable.flexbox_221);
        subType_productArrayList.add(R.drawable.flexbox_233 );
        subType_productArrayList.add(R.drawable.flexbox_234);
        subType_productArrayList.add(R.drawable.flexbox_235);
        subType_productArrayList.add(R.drawable.flexbox_236);
        subType_productArrayList.add(R.drawable.flexbox_237);
        subType_productArrayList.add(R.drawable.flexbox_238);
        subType_productArrayList.add(R.drawable.flexbox_239);
        subType_productArrayList.add(R.drawable.flexbox_240);
        subType_productArrayList.add(R.drawable.flexbox_1213_1);
        subType_productArrayList.add(R.drawable.superclassicgrey);
        subType_productArrayList.add(R.drawable.deltaflexbox);

        return subType_productArrayList;
    }
    /*********************************************************************************************
     * Name:-  getPower_Guards_Name()
     * Output :- ArrayList of String
     * Description :- Add all the power guard names to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getPower_Guards_Name() {

        subType_productNameArrayList.add("ZEL UNIVERSAL(1+4)");
        subType_productNameArrayList.add("SPLENDOR (1+4)");
        subType_productNameArrayList.add("NANO(1+4)");
        subType_productNameArrayList.add("NANO(4+4)");
        subType_productNameArrayList.add("NANO(1+6)");
        subType_productNameArrayList.add("DIYA(1+2)");
        subType_productNameArrayList.add("DIYA(1+3)");
        subType_productNameArrayList.add("DIYA(1+4)");
        subType_productNameArrayList.add("DIYA(4+4)");
        subType_productNameArrayList.add("DIYA(1+6)");
        subType_productNameArrayList.add("POD(1+2)");
        subType_productNameArrayList.add("POD(1+3)");
        subType_productNameArrayList.add("POD(1+4)");
        subType_productNameArrayList.add("POD(4+4)");
        subType_productNameArrayList.add("POD(1+6)");
        subType_productNameArrayList.add("MINT(1+2)");
        subType_productNameArrayList.add("MINT(1+3)");
        subType_productNameArrayList.add("MINT(1+4)");
        subType_productNameArrayList.add("MINT(4+4)");
        subType_productNameArrayList.add("MINT(1+6)");
        subType_productNameArrayList.add("CHARMS(1+2)");
        subType_productNameArrayList.add("CHARMS(1+3)");
        subType_productNameArrayList.add("CHARMS(1+4)");
        subType_productNameArrayList.add("CHARMS(4+4)");
        subType_productNameArrayList.add("CHARMS(1+6)");
        subType_productNameArrayList.add("PIVOT POWER STRIP");

        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getSpike_Guard_Name()
     * Output :- ArrayList of String
     * Description :- Add all the spikeguard names to the list and return the same
     *********************************************************************************************/
    public ArrayList<String> getSpike_Guard_Name() {
        // Spike Guard
        subType_productNameArrayList.add("ZEL UNIVERSAL");
        subType_productNameArrayList.add("ZEL UNIVERSAL");
        subType_productNameArrayList.add("UNO(1+4)");
        subType_productNameArrayList.add("UNO(1+5)");
        subType_productNameArrayList.add("UNO(1+6)");
        subType_productNameArrayList.add("PROTO(5+5)");
        subType_productNameArrayList.add("PROTO(6+6)");
        subType_productNameArrayList.add("PROTO(4+4)");
        subType_productNameArrayList.add("ELECTRA");

        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  getPower_Guard_Images()
     * Output :- ArrayList of String
     * Description :- Add all the power guard names to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getPower_Guard_Images() {

        subType_productArrayList.add(R.drawable.zelpowerguard);
        subType_productArrayList.add(R.drawable.splendorpowerguard);
        subType_productArrayList.add(R.drawable.nanopowerguard4);
        subType_productArrayList.add(R.drawable.nanopowerguard4_4);
        subType_productArrayList.add(R.drawable.nanopowerguard6);
        subType_productArrayList.add(R.drawable.diya2);
        subType_productArrayList.add(R.drawable.diya3);
        subType_productArrayList.add(R.drawable.diya4);
        subType_productArrayList.add(R.drawable.diya4_4);
        subType_productArrayList.add(R.drawable.diya6);
        subType_productArrayList.add(R.drawable.pod2);
        subType_productArrayList.add(R.drawable.pod3);

        subType_productArrayList.add(R.drawable.pod4);
        subType_productArrayList.add(R.drawable.pod4_4);
        subType_productArrayList.add(R.drawable.pod6);
        subType_productArrayList.add(R.drawable.mint2);
        subType_productArrayList.add(R.drawable.mint3);
        subType_productArrayList.add(R.drawable.mint4);
        subType_productArrayList.add(R.drawable.mint4_4);
        subType_productArrayList.add(R.drawable.mint6);
        subType_productArrayList.add(R.drawable.charms2);
        subType_productArrayList.add(R.drawable.charms3);
        subType_productArrayList.add(R.drawable.charms4);
        subType_productArrayList.add(R.drawable.charms4_4);
        subType_productArrayList.add(R.drawable.charms6);

        subType_productArrayList.add(R.drawable.pg_pivot);

        return subType_productArrayList;
    }

    /*********************************************************************************************
     * Name:-  getSpike_Guard_Name()
     * Output :- ArrayList of Integer
     * Description :- Add all the spike guard image to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> getSpike_Guard_Images() {

        // spike guard
        subType_productArrayList.add(R.drawable.zelspikeguard);
        subType_productArrayList.add(R.drawable.zelspikeguard1);
        subType_productArrayList.add(R.drawable.unospikeguard4);
        subType_productArrayList.add(R.drawable.unospikeguard5);
        subType_productArrayList.add(R.drawable.unospikeguard6);
        subType_productArrayList.add(R.drawable.protospikeguard4);
        subType_productArrayList.add(R.drawable.protospikeguard5);
        subType_productArrayList.add(R.drawable.protospikeguard6);

        subType_productArrayList.add(R.drawable.sg_electra);

        return subType_productArrayList;
    }

    /*********************************************************************************************
       Name :- getEVA_Range_List
       Output :- ArrayList of string
       Description :- Add all the string list od eva plates and return the same.
     *********************************************************************************************/
    public ArrayList<String> getEVA_Range_List(){

        subType_productNameArrayList.add("NATURAL");
        subType_productNameArrayList.add("METALLIC");
        subType_productNameArrayList.add("WOODEN");
        subType_productNameArrayList.add("MARBLE");

        return subType_productNameArrayList;
    }

    /*********************************************************************************************
     * Name:-  get_EVA_Images()
     * Output :- ArrayList of Integer
     * Description :- Add all the eva plastes image to the list and return the same
     *********************************************************************************************/
    public ArrayList<Integer> get_EVA_Images() {

        subType_productArrayList.add(R.drawable.edge_swich_cb_natural_small);
        subType_productArrayList.add(R.drawable.silver_edge);
        subType_productArrayList.add(R.drawable.wooden_edge);
        subType_productArrayList.add(R.drawable.eva_marble_edge);

        return subType_productArrayList;
    }
}

package stk.mobileoffice;

/**
 * Author: stk
 * Date: 2016/6/23
 * Time: 18:51
 */
public class TypeMap {
    public static String getBusniessType(String type) {
        switch (type) {
            case "2": return "重要商机";
            case "1":
            case "0":
            default: return "普通商机";
        }
    }

    public static String getOpportunityType(String type) {
        switch (type) {
            case "1": return "初步洽谈";
            case "2": return "需求确定";
            case "3": return "方案报价";
            case "4": return "谈判合同";
            case "5": return "赢单";
            case "6": return "输单";
            default: return "未知阶段";
        }
    }

    public static String getCustomerType(String type) {
        switch (type) {
            case "2": return "重要客户";
            case "1":
            case "0":
            default: return "普通客户";
        }
    }
}

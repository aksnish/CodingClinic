package com.ezdi.coding.clinic.utils;

public class RemoveASCII {
	 
    public static void main(String a[]){
        String str = " If��a��physician��documents��heart��failure��with preserved��ejection��fraction��(HFpEF),��heart��failure with��preserved��systolic��function,��and/or��heart��failure with��reduced��ejection��fraction��(HFrEF),��heart��failure with��low�"+"�ejection��fraction,��heart��failure��with��reduced systolic��function,��or��other��similar��terms,��can��the coder�� assume��the��provider�� means�� respectively ���diastolic��heart��failure�����or�����systolic��heart��failure,��� and��apply��the��proper��ICD-10-CM��code��based��on��the documented��clinical��circumstances?"+
    "Answer: No,��the��coder��cannot��assume��either��diastolic��or systolic��failure��or��a��combination��of��both,��based��on these��newer��terms.��Therefore,��query��the��provider�� to clarify��whether��the��patient��has��diastolic��or��systolic heart��failure.";
        System.out.println(str);
        str = str.replaceAll("[^\\p{ASCII}]", " ");
        System.out.println("After removing non ASCII chars:");
        System.out.println(str);
    }
}

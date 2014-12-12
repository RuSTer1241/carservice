package com.car.service.graphmode;

import com.car.service.database.DbEngine;
import com.car.service.listmode.ItemModel;
import com.car.service.model.CarServiceApplication;
import com.car.service.model.PreferenceEditor;
import com.jjoe64.graphview.GraphView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by r.savuschuk on 9/10/2014.
 */
public class GraphDataProcessor {

	DbEngine engine;
	PreferenceEditor prefEditor;
	public GraphDataProcessor(DbEngine engine) {
		this.engine=engine;
		prefEditor= CarServiceApplication.getPrefEditor();

	}

	public GraphView.GraphViewData[] initWeightData(List<ItemModel> itemDataList){
		GraphView.GraphViewData[] vdata= new GraphView.GraphViewData[itemDataList.size()];
		for(int i=0;i< itemDataList.size();i++){
			String s= itemDataList.get(i).getQuantity();
			double weight=0;
			String re1="(\\d{1,3})";	// Integer Number 1
			String re2="(.*?)";	// Non-greedy match on filler
			String re3="([a-z]{2,})";	// Variable Name 1
			String re4="(.*?)";	// Non-greedy match on filler
			String re5="(\\d{1,3})";	// Integer Number 1
			String re6="(.*?)";	// Non-greedy match on filler
			String re7="(([a-z]{1,}))";	// Variable Name 1*/

			Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(s);
			if (m.find()) {
				String val_1 = m.group(1);
				String empty1 = m.group(2);
				String txt1 = m.group(3);
				String empty2 = m.group(4);
				String val_2 = m.group(5);
				String empty3 = m.group(6);
				String txt2 = m.group(7);
				if(prefEditor.getMeasureUnit()){//if current unit is british
					if(m.group(3).startsWith("kg"))
						weight=(Integer.valueOf(val_1)*1000+Integer.valueOf(val_2))*0.002205F;//1g=0.002205ft
					if(m.group(3).startsWith("lb"))
						weight=Integer.valueOf(val_1)+(Integer.valueOf(val_2)*0.0625F);//1oz=0.0625lb
				}
				else{//current unit is metric
					if(m.group(3).startsWith("kg"))
						weight=(Integer.valueOf(val_1)*1000+Integer.valueOf(val_2))*0.001;
					if(m.group(3).startsWith("lb"))
						weight=((Integer.valueOf(val_1))*16+Integer.valueOf(val_2))*0.02835F;//1oz=0.02835kg

				}
				weight=(int)(weight*100);
				weight=weight/100;
			}
			vdata[i]= new GraphView.GraphViewData(i,weight);
		}
		return vdata;
	}

	public GraphView.GraphViewData[] initTemperatureData(List<ItemModel> itemDataList){

		//List<ItemModel> localItemsDataList=engine.getSeparateTypeRows(DbEngine.Action.TEMPERATURE.getValue());
		GraphView.GraphViewData[] vdata= new GraphView.GraphViewData[itemDataList.size()];
		for(int i=0;i<itemDataList.size();i++){
			String s=itemDataList.get(i).getQuantity();
			float temperature=0;
			String re1="(\\d+)";	// Integer Number 1
			String re2=".*?";	// Non-greedy match on filler
			String re3="(\\d+)";

			Pattern p = Pattern.compile(re1+re2+re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(s);
			if (m.find()){
				temperature= Float.valueOf(m.group(1))+Integer.valueOf(m.group(2))*0.1F;
			}
			vdata[i]= new GraphView.GraphViewData(i,temperature);
		}
		return vdata;
	}

	public GraphView.GraphViewData[] initEatData(List<ItemModel> itemDataList){
		GraphView.GraphViewData[] vdata= new GraphView.GraphViewData[itemDataList.size()];
		for(int i=0;i< itemDataList.size();i++){
			String s= itemDataList.get(i).getQuantity();
			float eat=0;
			String re1="(\\d+)";	// Integer Number 1

			Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(s);
			if (m.find())
			{
				eat=Integer.valueOf(m.group(1));
			}
			vdata[i]= new GraphView.GraphViewData(i,eat);
		}
		return vdata;
	}
}

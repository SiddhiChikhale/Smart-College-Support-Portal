package com.smartstudentportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.smartstudentportal.view.collegepage;

public class CollegeController {
    public static void addCollegeData(String name, String desc, List<String>features, String college_facilities) throws InterruptedException, ExecutionException{
        Map<String,Object> map = new HashMap<>();
        map.put("name", name);
        map.put("desc", desc);
        map.put("features", features);
        map.put("college_facilities", college_facilities);
        
        ApiFuture<WriteResult> docRef = FirestoreClient.getFirestore().collection("college").document(name).set(map);
        System.out.println(docRef.get().toString());
    }
    public static List<Map<String,Object>> getCollegeData() throws InterruptedException, ExecutionException{
        List<Map<String,Object>> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = FirestoreClient.getFirestore().collection("college").get();
        for(int i = 0;i<querySnapshot.get().getDocuments().size();i++){
            Map<String,Object> map = querySnapshot.get().getDocuments().get(i).getData();
            list.add(map);
        }
        return list;
    }
    public static collegepage getCollegeDetails(String str) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCollegeDetails'");
    }

    
}

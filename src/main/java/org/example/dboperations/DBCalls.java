package org.example.dboperations;

import org.example.models.Deal;
import org.example.models.enums.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DBCalls {

    private List<Deal> allDeals;
    public Deal saveDeal(Deal deal) throws Exception{
        try{
            deal.setId(UUID.randomUUID().toString());
            return insertDeal(deal, ActionType.SAVE);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private synchronized Deal insertDeal(Deal deal, ActionType type) throws Exception {
        switch (type){
            case SAVE ->{
                if(allDeals == null){
                    allDeals = new ArrayList<>();
                }
                allDeals.add(deal);
                return deal;
            }
            case UPDATE -> {
                int index = -1;
                for(int i=0;i<allDeals.size();i++){
                    if(allDeals.get(i).getId().equals(deal.getId())){
                        index=i;
                        break;
                    }
                }
                if(index == -1){
                    throw new Exception("deal not found !!");
                }
                allDeals.remove(index);
                allDeals.add(deal);
                return deal;
            }
            default -> {
                return null;
            }
        }
    }

    public Deal getDealById(String dealId) {
        for(Deal deal : allDeals){
            if(deal.getId().equals(dealId)){
                return deal;
            }
        }
        return null;
    }

    public Deal updateDeal(Deal deal) throws Exception {
        return insertDeal(deal,ActionType.UPDATE);
    }
}

package org.example.service;

import org.example.dboperations.DBCalls;
import org.example.models.ClaimDTO;
import org.example.models.CreateDealDTO;
import org.example.models.Deal;
import org.example.models.UpdateDTO;
import org.example.models.enums.DealState;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationLogic {

    @Autowired
    private DBCalls repository;

    public Deal processDeal(CreateDealDTO createDealDTO) throws Exception {
        try{
            if(createDealDTO.getHours() == 0 || createDealDTO.getName() == null || createDealDTO.getQuantity() == 0){
                throw new Exception("Invalid deal found !!");
            }
            Deal deal = new Deal();
            BeanUtils.copyProperties(createDealDTO,deal);
            deal.setEndTimeStamp(LocalDateTime.now().plusHours(createDealDTO.getHours()));
            deal.setUsersAlreadyBought(new ArrayList<>());
            deal.setState(DealState.ACTIVE);
            return repository.saveDeal(deal);
        }catch (Exception e){
            // log error
            throw e;
        }
    }

    public Deal handleEndDeal(String dealId) throws Exception {
        try{
            Deal deal = repository.getDealById(dealId);
            if(deal == null ){
                throw new Exception("Deal does not exist !!");
            }
            deal.setEndTimeStamp(LocalDateTime.now());
            deal.setEndedBefore(true);
            deal.setState(DealState.ENDED);
            return repository.updateDeal(deal);
        }catch (Exception e){
            throw e;
        }
    }

    public Deal updateDeal(UpdateDTO updateDTO) throws Exception {
        try{
            Deal deal = repository.getDealById(updateDTO.getId());
            if(deal == null ){
                throw new Exception("Deal does not exist !!");
            }
            if(deal.getState().equals(DealState.ENDED)){
                throw new Exception("Deal Already Ended !!");
            }
            deal.setQuantity(deal.getQuantity()+updateDTO.getQuantity());
            deal.setEndTimeStamp(deal.getEndTimeStamp().plusHours(updateDTO.getHours()));
            return  repository.updateDeal(deal);
        }catch (Exception e){
            throw e;
        }
    }

    public Deal processClaim(ClaimDTO claimDTO) throws Exception {
        try{
            Deal deal = repository.getDealById(claimDTO.getDealId());
            if(deal == null ){
                throw new Exception("Deal does not exist !!");
            }
            if(deal.getState().equals(DealState.ENDED)){
                throw new Exception("Deal already ended!!");
            }
            if(deal.getQuantity() == 0){
                throw new Exception("All Items sold !!");
            }
            if(deal.getUsersAlreadyBought().contains(claimDTO.getUserId())){
                throw new Exception("You already bought this deal!!");
            }
            if(deal.getEndTimeStamp().isBefore(LocalDateTime.now())){
                deal.setState(DealState.ENDED);
                repository.updateDeal(deal);
                throw new Exception("Deal has ended !!");
            }
            deal.setQuantity(deal.getQuantity()-1);
            List<String> users = deal.getUsersAlreadyBought();
            users.add(claimDTO.getUserId());
            deal.setUsersAlreadyBought(users);
            return repository.updateDeal(deal);
        }catch ( Exception e){
            throw e;
        }
    }
}

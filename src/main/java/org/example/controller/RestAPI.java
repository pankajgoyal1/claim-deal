package org.example.controller;


import org.apache.wink.json4j.JSONObject;
import org.example.models.ClaimDTO;
import org.example.models.CreateDealDTO;
import org.example.models.Deal;
import org.example.models.UpdateDTO;
import org.example.service.ApplicationLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestAPI {

    @Autowired
    private ApplicationLogic applicationLogic;

    @PostMapping("/create-deal")
    public ResponseEntity<Deal> createDeal(@RequestBody CreateDealDTO createDealDTO){
        try{
            Deal deal = applicationLogic.processDeal(createDealDTO);
            return new ResponseEntity<>(deal,HttpStatus.CREATED);
        }catch (Exception e){
            Deal errorDeal = new Deal();
            errorDeal.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(errorDeal,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/update-deal")
    public ResponseEntity<Deal> updateDeal(@RequestBody UpdateDTO updateDTO){
        try{
            Deal deal = applicationLogic.updateDeal(updateDTO);
            return new ResponseEntity<>(deal,HttpStatus.ACCEPTED);
        }catch (Exception e){
            Deal errorDeal = new Deal();
            errorDeal.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(errorDeal,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/claim-deal")
    public ResponseEntity<Deal> claimDeal(@RequestBody ClaimDTO claimDTO){
        try{
            Deal deal = applicationLogic.processClaim(claimDTO);
            return new ResponseEntity<>(deal,HttpStatus.ACCEPTED);
        }catch (Exception e){
            Deal errorDeal = new Deal();
            errorDeal.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(errorDeal,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/end-deal/deal/{dealId}")
    public ResponseEntity<Deal> endDeal(@PathVariable String dealId){
        try{
            Deal deal = applicationLogic.handleEndDeal(dealId);
            return new ResponseEntity<>(deal,HttpStatus.ACCEPTED);
        }catch (Exception e){
            Deal errorDeal = new Deal();
            errorDeal.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(errorDeal,HttpStatus.EXPECTATION_FAILED);
        }
    }
}

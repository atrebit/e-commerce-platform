package com.atrebit.inventory_service.service;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Service;
import com.atrebit.inventory_service.dto.domain.Order;
import com.odoojava.api.ObjectAdapter;
import com.odoojava.api.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OdooService {
    private final Session odooSession;

    public void createOrder(Order order) {
        try {
            ObjectAdapter partnerAd = odooSession.getObjectAdapter("res.partner");
            Object[] partnerIds = new Object[]{1, 2, 3};
            String[] fields = {"name", "email", "phone"};
            Object result = partnerAd.readObject(partnerIds, fields);
        } catch (XmlRpcException e) {
            log.error(String.format("XML-RPC error: %s", e.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Error while reading data from server: %s", e.getMessage()));
        }
    }
}
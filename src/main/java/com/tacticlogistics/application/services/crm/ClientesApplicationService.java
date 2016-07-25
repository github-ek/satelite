package com.tacticlogistics.application.services.crm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.crm.ClienteCanalAssociation;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;

@Service
@Transactional(readOnly = true)
public class ClientesApplicationService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Map<String, Object>> findCanalesPorCliente(Integer clienteId) throws DataAccessException {
        List<ClienteCanalAssociation> entityList = clienteRepository.findClienteCanalAssociationByClienteId(clienteId);
        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(canalToDto(a));
        });
        return list;
    }

    private Map<String, Object> canalToDto(ClienteCanalAssociation model) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("canalId", model.getCanalId());
        map.put("canalCodigoAlterno", model.getCodigoAlterno());

        return map;
    }
}

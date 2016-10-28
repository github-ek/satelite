package com.tacticlogistics.presentation.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.application.dto.wms.ProductoDto;
import com.tacticlogistics.application.services.wms.ProductosApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.wms.Producto;

@CrossOrigin
@RestController()
@RequestMapping("/productos")
public class ProductosController {
	@Autowired
	private ProductosApplicationService productosService;

	@RequestMapping("/unidades")
	public List<Object> getAllEstadoOrden() {
		List<Object> list = new ArrayList<>();

		try {
			list = productosService.findAllUnidades();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// TODO IMPLEMENTAR NIVEL POR DEFECTO POR UNIDAD
	@RequestMapping("/unidades-x-nivel")
	public List<Object> getAllEstadoOrden(@RequestParam(value = "nivel", required = true) Integer nivel) {
		List<Object> list = new ArrayList<>();

		try {
			for (Object object : productosService.findAllUnidades()) {
				@SuppressWarnings("unchecked")
				Map<String, Object> model = (Map<String, Object>) object;
				String codigo = (String)model.get("codigo");
				
				switch (nivel) {
				case 1:
					if ("UN".equals(codigo) || "KL".equals(codigo)) {
						list.add(model);
					}
					break;
				case 2:
					if ("CJ".equals(codigo) || "BU".equals(codigo)) {
						list.add(model);
					}
					break;
				case 3:
					if ("PA".equals(codigo) || "BG".equals(codigo)) {
						list.add(model);
					}
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/productos-x-cliente")
	public List<Object> getAllProductoPorCliente(
			@RequestParam(value = "id_cliente", required = true) Integer clienteId) {
		List<Object> list = new ArrayList<>();

		try {
			list = productosService.findAllProductoPorCliente(clienteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/unidades-x-producto")
	public List<Object> getAllUnidadPorProducto(
			@RequestParam(value = "id_producto", required = true) Integer productoId) {
		List<Object> list = new ArrayList<>();

		try {
			list = productosService.findAllUnidadPorProducto(productoId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// -- Save
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody ProductoDto dto) {
		Map<String, Object> respuesta = new HashMap<>();
		MensajesDTO<?> mensajes = new MensajesDTO<>();
		try {
			Producto model = this.productosService.save(dto);
			respuesta.put("producto", this.productosService.productoToDto(model));
			mensajes.add(SeveridadType.INFO, "");
		} catch (Exception e) {
			mensajes.add(e);
		}

		respuesta.put("mensajes", mensajes);
		return respuesta;
	}
}

package com.milsabores.backend.controller;

import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/webpay")
@CrossOrigin(origins = "*")
public class WebpayController {

    // Objeto para manejar las transacciones (instancia configurada)
    private WebpayPlus.Transaction tx;

    public WebpayController() {
        // Configuración para ambiente de Integración (TEST) usando WebpayOptions
        this.tx = new WebpayPlus.Transaction(new WebpayOptions(
            IntegrationCommerceCodes.WEBPAY_PLUS,
            IntegrationApiKeys.WEBPAY,
            IntegrationType.TEST
        ));
    }

    // 1. INICIAR TRANSACCIÓN
    // Usamos 'Object' como retorno para evitar errores de importación de clases específicas
    @PostMapping("/create")
    public Object create(@RequestBody Map<String, Object> paymentData) {
        try {
            String buyOrder = "ORDER-" + new Random().nextInt(100000);
            String sessionId = "SESSION-" + new Random().nextInt(100000);
            
            double amountDouble = Double.parseDouble(paymentData.get("amount").toString());
            int amount = (int) amountDouble;

            // URL de retorno (Ajusta localhost por tu IP si subes a AWS)
            String returnUrl = "http://localhost:8080/api/webpay/commit";

            // Creamos la transacción usando la instancia configurada
            WebpayPlusTransactionCreateResponse createResponse = tx.create(buyOrder, sessionId, amount, returnUrl);
            return createResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. CONFIRMAR TRANSACCIÓN
    @RequestMapping(value = "/commit", method = {RequestMethod.GET, RequestMethod.POST})
    public RedirectView commit(@RequestParam("token_ws") String tokenWs) {
        try {
            // Usamos 'var' (Java 10+) para que detecte el tipo de respuesta automáticamente
            // sin necesidad de importar la clase que estaba fallando.
            WebpayPlusTransactionCommitResponse response = tx.commit(tokenWs);

            // URL de tu Frontend React (Ajusta localhost por tu IP si subes a AWS)
            String frontendUrl = "http://localhost:3000"; 

            // Verificamos el estado (Compatible con v2.0)
            if (response.getStatus().equals("AUTHORIZED") || response.getResponseCode() == 0) {
                return new RedirectView(frontendUrl + "/payment-success?status=success&amount=" + response.getAmount());
            } else {
                return new RedirectView(frontendUrl + "/payment-success?status=failure");
            }

        } catch (Exception e) {
            e.printStackTrace();
            String frontendUrl = "http://localhost:3000";
            return new RedirectView(frontendUrl + "/payment-success?status=error");
        }
    }
}
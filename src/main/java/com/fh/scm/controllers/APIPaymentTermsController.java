package com.fh.scm.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/payment-terms", produces = "application/json; charset=UTF-8")
public class APIPaymentTermsController {
}

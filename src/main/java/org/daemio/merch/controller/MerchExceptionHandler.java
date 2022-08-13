package org.daemio.merch.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class MerchExceptionHandler implements ProblemHandling {

}

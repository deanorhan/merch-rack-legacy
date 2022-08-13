package org.daemio.merch.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class MerchNotFoundProblem extends AbstractThrowableProblem {
    
    public MerchNotFoundProblem() {
        super(null, "Not Found", Status.NOT_FOUND);
    }
}

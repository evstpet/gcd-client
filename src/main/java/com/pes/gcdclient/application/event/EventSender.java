package com.pes.gcdclient.application.event;

import com.pes.gcdclient.domain.vo.Calculation;

public interface EventSender {
    void sendEvent(Calculation calculation);
}

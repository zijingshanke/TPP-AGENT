package com.fordays.fdpay.agent.biz;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.neza.exception.AppException;

public interface CoterieBiz {
  Coterie getCoterieById(long id) throws AppException;
  Coterie getCoterieByPartner(String coterie) throws AppException;
  Coterie getCoterieByAgent(Agent agent) throws AppException;
  void setKeyByAgent(Agent agent,String key)throws AppException;
  boolean checkAgentInCoterie(Agent agent)throws AppException;
}

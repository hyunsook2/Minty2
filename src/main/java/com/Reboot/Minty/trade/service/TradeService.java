package com.Reboot.Minty.trade.service;

import com.Reboot.Minty.member.entity.User;
import com.Reboot.Minty.trade.entity.Trade;
import com.Reboot.Minty.trade.repository.TradeRepository;
import com.Reboot.Minty.tradeBoard.repository.TradeBoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final TradeBoardRepository tradeBoardRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository, TradeBoardRepository tradeBoardRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeBoardRepository = tradeBoardRepository;
    }

    public Page<Trade> getList(User user, Pageable pageable) {
        return tradeRepository.findAllByBuyerIdOrSellerId(user, user, pageable);
    }

    public Trade getTradeDetail(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId).orElse(null);
        if (trade == null) {
            // Trade가 존재하지 않는 경우 처리할 로직을 작성하세요.
        }

        return trade;
    }

    public Trade getReviewDetail(Long boardId) {
        Trade trade = tradeRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        return trade;
    }


    public Trade save(Trade trade){
        return tradeRepository.save(trade);
    }

    public String getRoleForTrade(Long tradeId, Long userId) {
        Trade trade = tradeRepository.findById(tradeId).orElse(null);
        if (trade == null) {
            // Trade가 존재하지 않는 경우 처리할 로직을 작성하세요.
        }

        String role = "";
        if (userId.equals(trade.getBuyerId().getId())) {
            role = "buyer";
        } else if (userId.equals(trade.getSellerId().getId())) {
            role = "seller";
        } else {
            // userId가 해당 거래의 구매자나 판매자가 아닌 경우 처리할 로직을 작성하세요.
        }

        return role;
    }
}

package com.querydsl.apt.domain;

import static org.junit.Assert.assertNotNull;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.junit.Test;

public class Generic10Test extends AbstractTest {

    public interface Tradable { }

    public interface Market<T extends Tradable> { }

    @Entity
    public static class FutureTrade implements Tradable { }

    @MappedSuperclass
    public abstract static class AbstractTradingMarket<T extends Tradable> implements Market<T> {

        @OneToOne
        private TradingMarketLedger<AbstractTradingMarket<T>> ledger;
    }

    @Entity
    public abstract static class AbstractFuturesMarket extends AbstractTradingMarket<FutureTrade> { }

    @Entity
    public static class CommonFuturesMarket extends AbstractFuturesMarket { }

    @Entity
    public static class TradingMarketLedger<M extends Market<? extends Tradable>> { }

    @Test
    public void test() {
        assertNotNull(QGeneric10Test_FutureTrade.Constants.futureTrade);

        start(QGeneric10Test_AbstractTradingMarket.class, QGeneric10Test_AbstractTradingMarket.Constants.abstractTradingMarket);
        assertPresent("ledger");

        start(QGeneric10Test_AbstractFuturesMarket.class, QGeneric10Test_AbstractFuturesMarket.Constants.abstractFuturesMarket);
        assertPresent("ledger");

        start(QGeneric10Test_CommonFuturesMarket.class, QGeneric10Test_CommonFuturesMarket.Constants.commonFuturesMarket);
        assertPresent("ledger");

        start(QGeneric10Test_TradingMarketLedger.class, QGeneric10Test_TradingMarketLedger.Constants.tradingMarketLedger);
    }
}

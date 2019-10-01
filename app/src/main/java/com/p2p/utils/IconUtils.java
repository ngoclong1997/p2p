package com.p2p.utils;

import com.p2p.R;

public class IconUtils {
    public static int getCoinIconByName(String name) {
        switch (name) {
            case "BTC":
                return R.drawable.ic_bitcoin;
            case "ETH":
                return R.drawable.ic_eth;
            case "EOS":
                return R.drawable.eos;
            case "LTC":
                return R.drawable.ltc;
            case "ZRX":
                return R.drawable.ic_zrx;
            case "USDC":
                return R.drawable.ic_usdc;
            case "BAT":
                return R.drawable.ic_bat;
            case "TRX":
                return R.drawable.trx;
            case "QTUM":
                return R.drawable.qtum;
            case "XRP":
                return R.drawable.xrp;
            case "DASH":
                return R.drawable.dash;
            case "BCH":
                return R.drawable.bch;
            case "ZEC":
                return R.drawable.zec;
            case "NEO":
                return R.drawable.neo;
            case "ETC":
                return R.drawable.etc;
            case "XLM":
                return R.drawable.xlm;
            case "PPN":
                return R.drawable.ic_logo;
            default:
                return R.drawable.ic_bitcoin;
        }
    }
}

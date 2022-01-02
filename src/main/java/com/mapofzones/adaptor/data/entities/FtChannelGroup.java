package com.mapofzones.adaptor.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@IdClass(FtChannelGroupKey.class)
@Table(name = "ft_channel_group_stats", schema = "public")
public class FtChannelGroup {
    @Id
    @Column(name = "zone")
    @NonNull
    private String zone;

    @Id
    @Column(name = "timeframe")
    @NonNull
    private Integer timeframe;

    @Column(name = "zone_counerparty")
    @NonNull
    private String counterpartyZone;

    @Column(name = "zone_label_url")
    private String zoneLabelUrl;

    @Column(name = "zone_counterparty_label_url")
    private String zoneCounterpartyLabelUrl;

    @Column(name = "ibc_cashflow_in")
    private BigInteger ibcCashflowIn;

    @Column(name = "ibc_cashflow_in_diff")
    private BigInteger ibcCashflowInDiff;

    @Column(name = "ibc_cashflow_out")
    private BigInteger ibcCashflowOut;

    @Column(name = "ibc_cashflow_out_diff")
    private BigInteger ibcCashflowOutDiff;

    @Column(name = "ibc_tx_success_rate")
    private Double ibcTxSuccessRate;

    @Column(name = "ibc_tx_success_rate_diff")
    private Double ibcTxSuccessRateDiff;

    @Column(name = "ibc_tx")
    @NonNull
    private BigInteger ibcTx;

    @Column(name = "ibc_tx_diff")
    @NonNull
    private BigInteger ibcTxDiff;

    @Column(name = "ibc_tx_failed")
    @NonNull
    private BigInteger ibcTxFailed;

    @Column(name = "ibc_tx_failed_diff")
    @NonNull
    private BigInteger ibcTxFailedDiff;

    @Column(name = "is_zone_up_to_date")
    @NonNull
    private Boolean isZoneUpToDate;

    @Column(name = "is_zone_counterparty_up_to_date")
    @NonNull
    private Boolean isZoneCounterpartyUpToDate;

    public FtChannelGroup() {
    }

    public FtChannelGroup(@NonNull String zone, @NonNull Integer timeframe, @NonNull String counterpartyZone, String zoneLabelUrl, String zoneCounterpartyLabelUrl, BigInteger ibcCashflowIn, BigInteger ibcCashflowInDiff, BigInteger ibcCashflowOut, BigInteger ibcCashflowOutDiff, Double ibcTxSuccessRate, Double ibcTxSuccessRateDiff, @NonNull BigInteger ibcTx, @NonNull BigInteger ibcTxDiff, @NonNull BigInteger ibcTxFailed, @NonNull BigInteger ibcTxFailedDiff, @NonNull Boolean isZoneUpToDate, @NonNull Boolean isZoneCounterpartyUpToDate) {
        this.zone = zone;
        this.timeframe = timeframe;
        this.counterpartyZone = counterpartyZone;
        this.zoneLabelUrl = zoneLabelUrl;
        this.zoneCounterpartyLabelUrl = zoneCounterpartyLabelUrl;
        this.ibcCashflowIn = ibcCashflowIn;
        this.ibcCashflowInDiff = ibcCashflowInDiff;
        this.ibcCashflowOut = ibcCashflowOut;
        this.ibcCashflowOutDiff = ibcCashflowOutDiff;
        this.ibcTxSuccessRate = ibcTxSuccessRate;
        this.ibcTxSuccessRateDiff = ibcTxSuccessRateDiff;
        this.ibcTx = ibcTx;
        this.ibcTxDiff = ibcTxDiff;
        this.ibcTxFailed = ibcTxFailed;
        this.ibcTxFailedDiff = ibcTxFailedDiff;
        this.isZoneUpToDate = isZoneUpToDate;
        this.isZoneCounterpartyUpToDate = isZoneCounterpartyUpToDate;
    }

    @NonNull
    public String getZone() {
        return zone;
    }

    public void setZone(@NonNull String zone) {
        this.zone = zone;
    }

    @NonNull
    public Integer getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(@NonNull Integer timeframe) {
        this.timeframe = timeframe;
    }

    @NonNull
    public String getCounterpartyZone() {
        return counterpartyZone;
    }

    public void setCounterpartyZone(@NonNull String counterpartyZone) {
        this.counterpartyZone = counterpartyZone;
    }

    public String getZoneLabelUrl() {
        return zoneLabelUrl;
    }

    public void setZoneLabelUrl(String zoneLabelUrl) {
        this.zoneLabelUrl = zoneLabelUrl;
    }

    public String getZoneCounterpartyLabelUrl() {
        return zoneCounterpartyLabelUrl;
    }

    public void setZoneCounterpartyLabelUrl(String zoneCounterpartyLabelUrl) {
        this.zoneCounterpartyLabelUrl = zoneCounterpartyLabelUrl;
    }

    public BigInteger getIbcCashflowIn() {
        return ibcCashflowIn;
    }

    public void setIbcCashflowIn(BigInteger ibcCashflowIn) {
        this.ibcCashflowIn = ibcCashflowIn;
    }

    public BigInteger getIbcCashflowInDiff() {
        return ibcCashflowInDiff;
    }

    public void setIbcCashflowInDiff(BigInteger ibcCashflowInDiff) {
        this.ibcCashflowInDiff = ibcCashflowInDiff;
    }

    public BigInteger getIbcCashflowOut() {
        return ibcCashflowOut;
    }

    public void setIbcCashflowOut(BigInteger ibcCashflowOut) {
        this.ibcCashflowOut = ibcCashflowOut;
    }

    public BigInteger getIbcCashflowOutDiff() {
        return ibcCashflowOutDiff;
    }

    public void setIbcCashflowOutDiff(BigInteger ibcCashflowOutDiff) {
        this.ibcCashflowOutDiff = ibcCashflowOutDiff;
    }

    public Double getIbcTxSuccessRate() {
        return ibcTxSuccessRate;
    }

    public void setIbcTxSuccessRate(Double ibcTxSuccessRate) {
        this.ibcTxSuccessRate = ibcTxSuccessRate;
    }

    public Double getIbcTxSuccessRateDiff() {
        return ibcTxSuccessRateDiff;
    }

    public void setIbcTxSuccessRateDiff(Double ibcTxSuccessRateDiff) {
        this.ibcTxSuccessRateDiff = ibcTxSuccessRateDiff;
    }

    @NonNull
    public BigInteger getIbcTx() {
        return ibcTx;
    }

    public void setIbcTx(@NonNull BigInteger ibcTx) {
        this.ibcTx = ibcTx;
    }

    @NonNull
    public BigInteger getIbcTxDiff() {
        return ibcTxDiff;
    }

    public void setIbcTxDiff(@NonNull BigInteger ibcTxDiff) {
        this.ibcTxDiff = ibcTxDiff;
    }

    @NonNull
    public BigInteger getIbcTxFailed() {
        return ibcTxFailed;
    }

    public void setIbcTxFailed(@NonNull BigInteger ibcTxFailed) {
        this.ibcTxFailed = ibcTxFailed;
    }

    @NonNull
    public BigInteger getIbcTxFailedDiff() {
        return ibcTxFailedDiff;
    }

    public void setIbcTxFailedDiff(@NonNull BigInteger ibcTxFailedDiff) {
        this.ibcTxFailedDiff = ibcTxFailedDiff;
    }

    @NonNull
    public Boolean getZoneUpToDate() {
        return isZoneUpToDate;
    }

    public void setZoneUpToDate(@NonNull Boolean zoneUpToDate) {
        isZoneUpToDate = zoneUpToDate;
    }

    @NonNull
    public Boolean getZoneCounterpartyUpToDate() {
        return isZoneCounterpartyUpToDate;
    }

    public void setZoneCounterpartyUpToDate(@NonNull Boolean zoneCounterpartyUpToDate) {
        isZoneCounterpartyUpToDate = zoneCounterpartyUpToDate;
    }
}
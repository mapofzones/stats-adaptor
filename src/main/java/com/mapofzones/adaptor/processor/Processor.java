package com.mapofzones.adaptor.processor;

import com.mapofzones.adaptor.constants.TimeframeConstants;
import com.mapofzones.adaptor.data.entities.*;
import com.mapofzones.adaptor.data.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Processor {
    private final HeaderRepository headerRepository;
    private final ZonesStatsRepository zonesStatsRepository;
    private final GraphRepository graphRepository;
    private final ChannelRepository channelRepository;
    private final FtChannelRepository ftChannelRepository;
    private final FtChannelGroupsRepository ftChannelGroupsRepository;
    private final ZoneStatusRepository zoneStatusRepository;

    public Processor(HeaderRepository headerRepository, ZonesStatsRepository zonesStatsRepository, GraphRepository graphRepository,
                     ChannelRepository channelRepository, FtChannelRepository ftChannelRepository, FtChannelGroupsRepository ftChannelGroupsRepository,
                     ZoneStatusRepository zoneStatusRepository) {
        this.headerRepository = headerRepository;
        this.zonesStatsRepository = zonesStatsRepository;
        this.graphRepository = graphRepository;
        this.channelRepository = channelRepository;
        this.ftChannelRepository = ftChannelRepository;
        this.ftChannelGroupsRepository = ftChannelGroupsRepository;
        this.zoneStatusRepository = zoneStatusRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Header> headers = new ArrayList<>();
        headers.add(headerRepository.getHeaderByTimeframe(TimeframeConstants.DAY, TimeframeConstants.DAY_STEP, true));
        headers.add(headerRepository.getHeaderByTimeframe(TimeframeConstants.WEEK, TimeframeConstants.WEEK_STEP, true));
        headers.add(headerRepository.getHeaderByTimeframe(TimeframeConstants.MONTH, TimeframeConstants.MONTH_STEP, true));
        headers.add(headerRepository.getHeaderByTimeframe(TimeframeConstants.DAY, TimeframeConstants.DAY_STEP, false));
        headers.add(headerRepository.getHeaderByTimeframe(TimeframeConstants.WEEK, TimeframeConstants.WEEK_STEP, false));
        headers.add(headerRepository.getHeaderByTimeframe(TimeframeConstants.MONTH, TimeframeConstants.MONTH_STEP, false));
        System.out.println("ready to save headers");
        headerRepository.saveAll(headers);
        System.out.println("Header adaptor finished!");

        List<ZoneStats> zonesStats = zonesStatsRepository.getZonesStatsByTimeframe(TimeframeConstants.DAY, TimeframeConstants.DAY_STEP);
        zonesStats.addAll(zonesStatsRepository.getZonesStatsByTimeframe(TimeframeConstants.WEEK, TimeframeConstants.WEEK_STEP));
        zonesStats.addAll(zonesStatsRepository.getZonesStatsByTimeframe(TimeframeConstants.MONTH, TimeframeConstants.MONTH_STEP));
        System.out.println("ready to save zones_stats");
        zonesStatsRepository.saveAll(zonesStats);
        System.out.println("zones_stats adaptor finished!");

        List<Graph> graphs = graphRepository.getGraphsByTimeframe(TimeframeConstants.DAY);
        graphs.addAll(graphRepository.getGraphsByTimeframe(TimeframeConstants.WEEK));
        graphs.addAll(graphRepository.getGraphsByTimeframe(TimeframeConstants.MONTH));
        System.out.println("ready to save graphs");
        graphRepository.saveAll(graphs);
        System.out.println("graphs adaptor finished!");

        List<Channel> channels = channelRepository.getChannelsStats();
        System.out.println("ready to save channels");
        channelRepository.saveAll(channels);
        System.out.println("channels adaptor finished!");

        List<FtChannel> ftChannels = ftChannelRepository.getFtChannelsStats(TimeframeConstants.DAY_STEP);
        ftChannels.addAll(ftChannelRepository.getFtChannelsStats(TimeframeConstants.WEEK_STEP));
        ftChannels.addAll(ftChannelRepository.getFtChannelsStats(TimeframeConstants.MONTH_STEP));
        System.out.println("ready to save ft channels");
        ftChannelRepository.saveAll(ftChannels);
        System.out.println("ft channels adaptor finished!");


        List<ZoneStatus> zoneStatusesList = zoneStatusRepository.getZoneStatuses();
        Map<String, ZoneStatus> zoneStatuses = new HashMap<>();
        for (ZoneStatus zoneStatus: zoneStatusesList) {
            zoneStatuses.put(zoneStatus.getZone(), zoneStatus);
        }
        List<FtChannelGroup> ftChannelGroups = getFtChannelGroups(ftChannels, zoneStatuses);
        System.out.println("ready to save ft channels groups");
        ftChannelGroupsRepository.saveAll(ftChannelGroups);
        System.out.println("ft channels groups adaptor finished!");

        System.out.println("Finished!");
        System.out.println("---------------");
        System.exit(0);
    }

    private List<FtChannelGroup> getFtChannelGroups(List<FtChannel> ftChannels, Map<String, ZoneStatus> zoneStatuses){
        List<FtChannelGroup> result = new ArrayList<>();
        HashMap<String, HashMap<Integer, FtChannelGroup>> channelGroups = new HashMap<>();
        for (FtChannel ftChannel: ftChannels) {
            String zone = ftChannel.getZone();
            Integer timeframe = ftChannel.getTimeframe();
            if (!channelGroups.containsKey(zone)) {
                FtChannelGroup ftChannelGroup = createChannelGroup(zoneStatuses, ftChannel, zone);
                HashMap<Integer, FtChannelGroup> hashGroup = new HashMap<>() {{ put(timeframe, ftChannelGroup);}};
                channelGroups.put(zone, hashGroup);
            } else {
                if (!channelGroups.get(zone).containsKey(timeframe)) {
                    FtChannelGroup ftChannelGroup = createChannelGroup(zoneStatuses, ftChannel, zone);
                    channelGroups.get(zone).put(timeframe, ftChannelGroup);
                } else {
                    FtChannelGroup ftChannelGroup = channelGroups.get(zone).get(timeframe);
                    ftChannelGroup.setIbcCashflowIn(ftChannelGroup.getIbcCashflowIn().add(ftChannel.getIbcCashflowIn()));
                    ftChannelGroup.setIbcCashflowInDiff(ftChannelGroup.getIbcCashflowInDiff().add(ftChannel.getIbcCashflowInDiff()));
                    ftChannelGroup.setIbcCashflowOut(ftChannelGroup.getIbcCashflowOut().add(ftChannel.getIbcCashflowOut()));
                    ftChannelGroup.setIbcCashflowOutDiff(ftChannelGroup.getIbcCashflowOutDiff().add(ftChannel.getIbcCashflowOutDiff()));
                    ftChannelGroup.setIbcTx(ftChannelGroup.getIbcTx().add(BigInteger.valueOf(ftChannel.getIbcTx())));
                    ftChannelGroup.setIbcTxDiff(ftChannelGroup.getIbcTxDiff().add(BigInteger.valueOf(ftChannel.getIbcTxDiff())));
                    ftChannelGroup.setIbcTxFailed(ftChannelGroup.getIbcTxFailed().add(BigInteger.valueOf(ftChannel.getIbcTxFailed())));
                    ftChannelGroup.setIbcTxFailedDiff(ftChannelGroup.getIbcTxFailedDiff().add(BigInteger.valueOf(ftChannel.getIbcTxFailedDiff())));
                }
            }
        }

        for (HashMap<Integer, FtChannelGroup> linkedGroups: channelGroups.values()) {
            result.addAll(linkedGroups.values());
        }

        return result;
    }

    private FtChannelGroup createChannelGroup(Map<String, ZoneStatus> zoneStatuses, FtChannel ftChannel, String zone) {
        return new FtChannelGroup(
                ftChannel.getZone(),
                ftChannel.getTimeframe(),
                ftChannel.getCounterpartyZone(),
                ftChannel.getZoneLabelUrl(),
                ftChannel.getZoneCounterpartyLabelUrl(),
                ftChannel.getIbcCashflowIn(),
                ftChannel.getIbcCashflowInDiff(),
                ftChannel.getIbcCashflowOut(),
                ftChannel.getIbcCashflowOutDiff(),
                ftChannel.getIbcTxSuccessRate(),
                ftChannel.getIbcTxSuccessRateDiff(),
                BigInteger.valueOf(ftChannel.getIbcTx()),
                BigInteger.valueOf(ftChannel.getIbcTxDiff()),
                BigInteger.valueOf(ftChannel.getIbcTxFailed()),
                BigInteger.valueOf(ftChannel.getIbcTxFailedDiff()),
                zoneStatuses.get(zone).getZoneUpToDate(),
                zoneStatuses.get(ftChannel.getCounterpartyZone()).getZoneUpToDate()
        );
    }
}

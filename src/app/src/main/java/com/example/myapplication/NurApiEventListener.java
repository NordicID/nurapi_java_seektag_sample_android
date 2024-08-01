package com.example.myapplication;

import android.util.Log;

import com.nordicid.nurapi.NurApi;
import com.nordicid.nurapi.NurApiListener;
import com.nordicid.nurapi.NurEventAutotune;
import com.nordicid.nurapi.NurEventClientInfo;
import com.nordicid.nurapi.NurEventDeviceInfo;
import com.nordicid.nurapi.NurEventEpcEnum;
import com.nordicid.nurapi.NurEventFrequencyHop;
import com.nordicid.nurapi.NurEventIOChange;
import com.nordicid.nurapi.NurEventInventory;
import com.nordicid.nurapi.NurEventNxpAlarm;
import com.nordicid.nurapi.NurEventProgrammingProgress;
import com.nordicid.nurapi.NurEventTagTrackingChange;
import com.nordicid.nurapi.NurEventTagTrackingData;
import com.nordicid.nurapi.NurEventTraceTag;
import com.nordicid.nurapi.NurEventTriggeredRead;
import com.nordicid.nurutils.NurUtils;
import com.nordicid.nurutils.seektag.NearestTagChangeEventArgs;
import com.nordicid.nurutils.seektag.SeekNearestTag;
import com.nordicid.nurutils.seektag.SeekNearestTagListener;

/**
 * This is an example nur api event listener, to handle nur-related event actions.
 * For the sake of simplicity, I will directly use this listener for the referenced SeekNearestTag Utility.
 * IMPORTANT: Do not block in these event handler methods, it will cause unexpected behavior with NurApi, you can create threads, tasks, signal status with variables, events and so on.
 */
public class NurApiEventListener implements NurApiListener {

    private final NurApi nurApi;
    private final SeekNearestTag seekNearestTag;

    public NurApiEventListener(NurApi nurApi) {
        this.nurApi = nurApi;
        this.seekNearestTag = new SeekNearestTag(this.nurApi);
        setupSeekNearestTag(); // Configure seek & set listener for seek status changes.
    }

    /**
     * Setting up tag seek with the desired configuration & registering the SeekNearestTagListener.
     */
    public void setupSeekNearestTag() {

        // Configuring the tag seek initially.
        seekNearestTag.mustSeenCountBeforeAcceptBest = 2; // How many tags must be seen before the best is accepted.
        seekNearestTag.setSingleTagOnly(true); // We are interested of one tag only, if not, you can select false for multi tag seek.

        // seekNearestTag.addTagToBlackList("TAG_EPC_HEX_HERE"); // Additionally tags can be blacklisted by epc..
        // seekNearestTag.addTagToIgnoreList("TAG_EPC_HEX_HERE"); // Ignore specific tags
        // seekNearestTag.allowEpcToStartWith("START_OF_SOME_EPC_HEX"); // Seek tags that start with given EPC HEX string.
        // String currentNearestEPC = seekNearestTag.getCurrentNearestEPC(); // The currently nearest EPC from the tag seek.
        // .. And so on, there are various settings available in the SeekNearestTag class.

        // Registering custom listener to tag seek:
        seekNearestTag.addSeekNearestTagListener(new SeekNearestTagListener() {
            @Override
            public void onStatusChanged(NearestTagChangeEventArgs eventArgs) {
                // Sample logging of status changes during the tag seek.
                Log.d("tag-seek-state", "Seek status changed: " + eventArgs.getSeekStatus().toString());
                if (eventArgs.getMsg() != null) {
                    Log.d("tag-seek-debug", "Seek msg: " + eventArgs.getMsg());
                }
                if (eventArgs.getTagSeen() != null) {
                    Log.d("tag-seek-debug", "Seen tag: " + eventArgs.getTagSeen());
                }
            }
        });
    }

    @Override
    public void logEvent(int nurLogLevel, String message) {
        Log.d("nur-internal-log-" + nurLogLevel, message);
    }

    @Override
    public void connectedEvent() {
        Log.w("nur-conn-events", "Device has been connected.");

        try {
            nurApi.setSetupTxLevel(NurUtils.getTxLevelByPercentage(nurApi, 45)); // By utilizing the nur util library, an initial TxLevel (Transmission Level) power can be set with a percentage range of 0-100.

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // We can start seek already here since we connected, not most elegant but fine for a sample demo.
        seekNearestTag.start(); // Tag seek starts and internally a new thread is created where the seek is ongoing. You can stop the seek gracefully by calling seekNearestTag.stop();
    }

    @Override
    public void disconnectedEvent() {
        // Since we are disconnected, we cannot seek tags. Trying to stop gracefully:
        if (seekNearestTag.isRunning()) {
            seekNearestTag.stop();
        }
        Log.w("nur-conn-events", "Device has been disconnected.");
    }

    @Override
    public void bootEvent(String s) {

    }

    @Override
    public void inventoryStreamEvent(NurEventInventory nurEventInventory) {

    }

    @Override
    public void IOChangeEvent(NurEventIOChange nurEventIOChange) {

    }

    @Override
    public void traceTagEvent(NurEventTraceTag nurEventTraceTag) {

    }

    @Override
    public void triggeredReadEvent(NurEventTriggeredRead nurEventTriggeredRead) {

    }

    @Override
    public void frequencyHopEvent(NurEventFrequencyHop nurEventFrequencyHop) {

    }

    @Override
    public void debugMessageEvent(String message) {
        Log.d("nur-internal-dbg", message);
    }

    @Override
    public void inventoryExtendedStreamEvent(NurEventInventory nurEventInventory) {

    }

    @Override
    public void programmingProgressEvent(NurEventProgrammingProgress nurEventProgrammingProgress) {

    }

    @Override
    public void deviceSearchEvent(NurEventDeviceInfo nurEventDeviceInfo) {

    }

    @Override
    public void clientConnectedEvent(NurEventClientInfo nurEventClientInfo) {

    }

    @Override
    public void clientDisconnectedEvent(NurEventClientInfo nurEventClientInfo) {

    }

    @Override
    public void nxpEasAlarmEvent(NurEventNxpAlarm nurEventNxpAlarm) {

    }

    @Override
    public void epcEnumEvent(NurEventEpcEnum nurEventEpcEnum) {

    }

    @Override
    public void autotuneEvent(NurEventAutotune nurEventAutotune) {

    }

    @Override
    public void tagTrackingScanEvent(NurEventTagTrackingData nurEventTagTrackingData) {

    }

    @Override
    public void tagTrackingChangeEvent(NurEventTagTrackingChange nurEventTagTrackingChange) {

    }
}

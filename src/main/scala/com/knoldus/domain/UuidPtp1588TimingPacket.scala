package com.knoldus.domain

import java.util.UUID

/**
  * Created by slee on 3/7/17.
  */
case class UuidPtp1588TimingPacket(timeBucket: Long,
                                   experimentId: UUID,
                                   macAddress: Long,
                                   syncReceiveS: Int,
                                   syncReceiveNs: Int,
                                   delayRequestSendS: Int,
                                   delayRequestSendNs: Int,
                                   delayRequestReceiveS: Int,
                                   delayRequestReceiveNs: Int,
                                   offsetFromMasterS: Int,
                                   offsetFromMasterNS: Int,
                                   meanPathDelayS: Int,
                                   meanPathDelayNs: Int,
                                   masterToSlaveDelayS: Int,
                                   masterToSlaveDelayNs: Int,
                                   slaveToMasterDelayS: Int,
                                   slaveToMasterDelayNs: Int,
                                   portState: Int)

package com.bakalov.flowerscompany.service;

import java.io.IOException;

public interface ScheduleService {

  void writeTodayLogs() throws IOException;

  void writeTodayBatches() throws IOException;
}

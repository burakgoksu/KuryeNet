package com.gp.KuryeNet.core.business.concretes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.business.abstracts.SchedulerService;

@Service
public class SchedulerManager implements SchedulerService{
    private ScheduledExecutorService scheduler;

    public SchedulerManager() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public ScheduledExecutorService getScheduler() {
        if (scheduler.isShutdown() || scheduler.isTerminated()) {
            scheduler = Executors.newScheduledThreadPool(1); // Yeniden başlat
        }
        return scheduler;
    }

	@Override
	public void shutdownScheduler() {
		 if (!scheduler.isShutdown()) {
	            scheduler.shutdown();
	        }
		
	}
}

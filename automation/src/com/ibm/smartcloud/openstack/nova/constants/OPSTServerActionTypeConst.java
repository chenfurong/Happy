package com.ibm.smartcloud.openstack.nova.constants;

/**
 * 实例操作枚举。
 * 
 * @author xucd
 *
 */
public enum OPSTServerActionTypeConst {// addFloatingIp
	OSSTOP, OSSTART, TERMINATES, RESTORE, PAUSED, UNPAUSED, SUSPEND, RESUME, SNAPSHOT, RECOVER, REBOOT, HARDREBOOT, CREATEIMAGE, CONFIRMRESIZE, REVERTRESIZE, ADDFLOATINGIP, REMOVEFLOATINGIP, SERVERSHELVE, SERVERUNSHELVE;

	public String getAction() {
		switch (this) {
		case OSSTOP:
			return "os-stop";
		case OSSTART:
			return "os-start";
		case TERMINATES:
			return "terminates";
			// 恢复
		case RESTORE:
			return "restore";
			// 暂停
		case PAUSED:
			return "paused";
			// 恢复暂停的实例
		case UNPAUSED:
			return "unpause";
			// 挂起
		case SUSPEND:
			return "suspend";
			// 激活挂起的实例
		case RESUME:
			return "resume";
			// 数据盘快照
		case SNAPSHOT:
			return "snapshot";
			// 回收
		case RECOVER:
			return "recover";
			// 软重启
		case REBOOT:
			return "reboot";
			// 硬重启
		case HARDREBOOT:
			return "hard-reboot";
			// 创建数据盘快照
		case CREATEIMAGE:
			return "createImage";
			// 确认调整大小
		case CONFIRMRESIZE:
			return "confirmResize";
			// 取消调整大小
		case REVERTRESIZE:
			return "revertResize";
		case ADDFLOATINGIP:
			return "addFloatingIp";
		case REMOVEFLOATINGIP:
			return "removeFloatingIp";
			// 休眠
		case SERVERSHELVE:
			return "shelve";
			// 取消休眠
		case SERVERUNSHELVE:
			return "unshelve";
		default:
			return "";
		}
	}
}


package com.bootx.mall.auth.plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import com.bootx.mall.auth.entity.PluginConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

/**
 * Plugin - FTP存储
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component("ftpStoragePlugin")
public class FtpStoragePlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "FTP存储";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "好源++";
	}

	@Override
	public String getSiteUrl() {
		return "localhost";
	}

	@Override
	public String getInstallUrl() {
		return "/admin/plugin/ftp_storage/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/ftp_storage/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/ftp_storage/setting";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String host = pluginConfig.getAttribute("host");
			Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			String username = pluginConfig.getAttribute("username");
			String password = pluginConfig.getAttribute("password");
			FTPClient ftpClient = new FTPClient();
			InputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					String directory = StringUtils.substringBeforeLast(path, "/");
					String filename = StringUtils.substringAfterLast(path, "/");
					if (!ftpClient.changeWorkingDirectory(directory)) {
						String[] paths = StringUtils.split(directory, "/");
						String p = "/";
						ftpClient.changeWorkingDirectory(p);
						for (String s : paths) {
							p += s + "/";
							if (!ftpClient.changeWorkingDirectory(p)) {
								ftpClient.makeDirectory(s);
								ftpClient.changeWorkingDirectory(p);
							}
						}
					}
					ftpClient.storeFile(filename, inputStream);
					ftpClient.logout();
				}
			} catch (SocketException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

}
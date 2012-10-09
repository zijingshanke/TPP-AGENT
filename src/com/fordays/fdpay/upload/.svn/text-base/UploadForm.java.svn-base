package com.fordays.fdpay.upload;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadForm extends ActionForm {
	private FormFile uploadFile;
	private int objId = 0;
	private String path = "";
	private String thisAction = "";
	private String fileKey = "";
	private String listAttachName = "listattch";
	private ArrayList listAttach = new ArrayList();




	public String getListAttachName() {
		return listAttachName;
	}

	public void setListAttachName(String listAttachName) {
		this.listAttachName = listAttachName;
	}

	public ArrayList getListAttach() {
		return listAttach;
	}

	public int getListAttachNum() {

		if (listAttach != null)
			return listAttach.size();
		else
			return 0;
	}

	public void setListAttach(ArrayList listAttach) {
		this.listAttach = listAttach;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public int getObjId() {
		return objId;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}

	public String getPath() {
		return path;

	}

	public void setPath(String path) {
		this.path = path;

	}

	public String getThisAction() {
		return thisAction;
	}

	public void setThisAction(String thisAction) {
		this.thisAction = thisAction;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
}
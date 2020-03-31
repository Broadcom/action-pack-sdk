package com.broadcom.apdk.helpers;

import java.util.ArrayList;
import java.util.List;

import com.broadcom.apdk.objects.Folder;
import com.broadcom.apdk.objects.IAutomicContent;
import com.broadcom.apdk.objects.IAutomicContentFolder;
import com.broadcom.apdk.objects.IAutomicContentLink;
import com.broadcom.apdk.objects.IAutomicContentReference;
import com.broadcom.apdk.objects.IAutomicObject;
import com.broadcom.apdk.objects.IStorage;
import com.broadcom.apdk.objects.StoredFile;

public class FolderHelper {
	
	private static IAutomicContentFolder insertObjectRecursive(IAutomicContentFolder rootFolder, 
			String parentFolderPath, IAutomicContent newObject) {
		if (newObject != null) {
			List<IAutomicContent> folderContent = rootFolder.getObjects();		
			if (parentFolderPath != null && !parentFolderPath.isEmpty()) {
				String[] folderPathItems = parentFolderPath.split("/");
				if (folderPathItems.length > 0) {
					List<IAutomicContent> newFolderContent = new ArrayList<IAutomicContent>();
					boolean foundFolder = false;
					if (folderContent != null) {
						for (IAutomicContent contentObject : folderContent) {
							IAutomicContent newContentObject = (IAutomicContent) contentObject;
							if (IAutomicContentFolder.class.isAssignableFrom(contentObject.getClass())) {
								IAutomicContentFolder folder = (IAutomicContentFolder) contentObject;
								if (((IAutomicContentFolder) contentObject).getName().equals(folderPathItems[0])) {
									foundFolder = true;
									String newParentFolderPath = parentFolderPath.substring(folderPathItems[0].length());
									if (newParentFolderPath.startsWith("/")) {
										newParentFolderPath = newParentFolderPath.substring(1);	
									}
									newContentObject = insertObjectRecursive(folder, newParentFolderPath, newObject);
								}
							}
							newFolderContent.add(newContentObject);
						}
					}
					if (!foundFolder) {
						IAutomicContentFolder folder = new Folder(folderPathItems[0]);
						String newParentFolderPath = parentFolderPath.substring(folderPathItems[0].length());
						if (newParentFolderPath.startsWith("/")) {
							newParentFolderPath = newParentFolderPath.substring(1);	
						}
						IAutomicContent newContentObject = insertObjectRecursive(folder, newParentFolderPath, newObject);
						newFolderContent.add(newContentObject);
					}
					rootFolder.setObjects(newFolderContent);
				}
			}
			else {
				if (folderContent == null) {
					folderContent = new ArrayList<IAutomicContent>();
				}
				if (!FolderHelper.contains(folderContent, newObject)) {
					folderContent.add(newObject);	
				}
				rootFolder.setObjects(folderContent);
			}
		}
		return rootFolder;
	}
	
	private static boolean contains(List<IAutomicContent> folderContent, IAutomicContent newObject) {
		if (folderContent != null && !folderContent.isEmpty() && newObject != null) {
			for (IAutomicContent obj : folderContent) {
				if (IAutomicContentFolder.class.isAssignableFrom(obj.getClass()) &&
						IAutomicContentFolder.class.isAssignableFrom(newObject.getClass()) &&
						((IAutomicContentFolder) newObject).getName().equals(((IAutomicContentFolder) obj).getName())) {
					return true;
				}
				else if (IAutomicContentReference.class.isAssignableFrom(obj.getClass()) &&
						IAutomicContentReference.class.isAssignableFrom(newObject.getClass()) &&
						((IAutomicContentReference) newObject).getName().equals(((IAutomicContentReference) obj).getName())) {
					return true;
				}
				else if (IAutomicObject.class.isAssignableFrom(obj.getClass()) &&
						IAutomicObject.class.isAssignableFrom(newObject.getClass()) &&
						((IAutomicObject) newObject).getName().equals(((IAutomicObject) obj).getName())) {
					return true;
				}
				else if (IAutomicContentLink.class.isAssignableFrom(obj.getClass()) &&
						IAutomicContentLink.class.isAssignableFrom(newObject.getClass())) {
					IAutomicObject obj1 = ((IAutomicContentLink) newObject).getObject();
					IAutomicObject obj2 = ((IAutomicContentLink) obj).getObject();
					if (obj1 != null && obj2 != null && obj1.getName().equals(obj2.getName())) {
						return true;
					}
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	
	public static IAutomicContentFolder insertObject(IAutomicContentFolder rootFolder, 
			String parentFolderPath, IAutomicContent newObject) {
		if (rootFolder != null) {
			rootFolder = insertObjectRecursive(rootFolder, parentFolderPath, newObject);
		}
		return rootFolder;
	}
	
	public static List<String> getFilenames(String actionPackName, IAutomicContentFolder rootFolder) {
		List<IAutomicObject> objects = getAutomicObjects(rootFolder);
		List<String> filenames = new ArrayList<String>();
		if (objects != null) {
			for (IAutomicObject object : objects) {
				if (IStorage.class.isAssignableFrom(object.getClass())) {
					IStorage storageObject = (IStorage) object;
					List<StoredFile> fileInfos = storageObject.getStoredFiles();
					if (fileInfos != null) {
						for (StoredFile fileInfo : fileInfos) {
							String props = fileInfo.getPlatform() + "-" + fileInfo.getOs() + "-" + fileInfo.getArchitecture();
							filenames.add(storageObject.getName() + "-" + fileInfo.getName() + "-" + props);	
						}
					}
				}
			}
		}
		return filenames;
	}
	
	public static List<IAutomicObject> getAutomicObjects(IAutomicContentFolder rootFolder) {
		List<IAutomicObject> objects = new ArrayList<IAutomicObject>();
		List<IAutomicContent> folderContent = rootFolder.getObjects(); 
		if (folderContent != null) {
			for (IAutomicContent contentObject : folderContent) {
				IAutomicContent object = (IAutomicContent) contentObject;
				if (IAutomicObject.class.isAssignableFrom(object.getClass())) {
					objects.add((IAutomicObject) object);
				}
				if (IAutomicContentFolder.class.isAssignableFrom(object.getClass())) {
					List<IAutomicObject> addObjects = getAutomicObjects((IAutomicContentFolder) object);
					if (addObjects != null) {
						objects.addAll(addObjects);
					}
				}
			}
			return objects;
		}
		return null;
	}

}

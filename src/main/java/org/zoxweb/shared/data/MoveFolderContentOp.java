package org.zoxweb.shared.data;



import java.util.List;

import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class MoveFolderContentOp
extends SetNameDescriptionDAO
{
	  public enum Param
      implements GetNVConfig
	  {
	      FROM_FOLDER_REF(NVConfigManager.createNVConfig("from_folder_ref", "From folder reference id","FromFolderRef", true, false, String.class)),
	      TO_FOLDER_REF(NVConfigManager.createNVConfig("to_folder_ref", "To folder reference id", "ToFolderRef", true, true, String.class)),
	      NVES_REF(NVConfigManager.createNVConfig("nves_ref", "List of nves reference id", "NVESRef", true, true, String[].class)),
	      ;
	
	      private final NVConfig nvc;
	
	      Param(NVConfig nvc)
	      {
	          this.nvc = nvc;
	      }
	
	      public NVConfig getNVConfig()
	      {
	          return nvc;
	      }
	  }
	
	  public static final NVConfigEntity NVC_MOVE_FOLDER_CONTENT_OP = new NVConfigEntityLocal(
	          "move_folder_content_op",
	          "AppIDDAO" ,
	          MoveFolderContentOp.class.getSimpleName(),
	          true, false,
	          false, false,
	          MoveFolderContentOp.class,
	          SharedUtil.extractNVConfigs(Param.values()),
	          null,
	          false,
	          SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
	  );
	  
	  
	  public MoveFolderContentOp()
	  {
		  super(NVC_MOVE_FOLDER_CONTENT_OP);
	  }
	  
	  
	  public MoveFolderContentOp(FolderInfoDAO fromFolder, FolderInfoDAO toFolder, List<NVEntity> list)
	  {
		  this();
		  setFromFolder(fromFolder);
		  setToFolder(toFolder);
		 
		  for (NVEntity nve : list)
		  {
			  addNVEToContent(nve);
		  }
	  }
	  
	  public void setFromFolder(FolderInfoDAO folder)
	  {
		  setFromFolderRef(folder.getReferenceID());
	  }
	  
	  public void setFromFolderRef(String fromFolderRef)
	  {
		  setValue(Param.FROM_FOLDER_REF, fromFolderRef);
	  }
	  
	  public String getFromFolderRef()
	  {
		  return lookupValue(Param.FROM_FOLDER_REF);
	  }
	  
	  public void setToFolder(FolderInfoDAO folder)
	  {
		  setToFolderRef(folder.getReferenceID());
	  }
	  
	  public void setToFolderRef(String ToFolderRef)
	  {
		  setValue(Param.TO_FOLDER_REF, ToFolderRef);
	  }
	  
	  public String getToFolderRef()
	  {
		  return lookupValue(Param.TO_FOLDER_REF);
	  }
	  
	  @SuppressWarnings("unchecked")
	  public ArrayValues<NVPair> getNVEs()
	  {
		  return (ArrayValues<NVPair>)lookup(Param.NVES_REF);
	  }
	  
	  public void addNVEToContent(NVEntity nve)
	  {
		  getNVEs().add(new NVPair(nve.getName() != null ? nve.getName() : nve.getReferenceID(), nve.getReferenceID()));
	  }

}

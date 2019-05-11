package org.zoxweb.server.http;

import java.util.List;
import org.zoxweb.server.util.GSONUtil;

import org.zoxweb.shared.http.HTTPResponseData;
import org.zoxweb.shared.util.DataDecoder;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SharedBase64.Base64Type;
import org.zoxweb.shared.util.SharedStringUtil;

public abstract class HTTPDecoder<DI, DO>
implements DataDecoder<DI, DO>
{

  public static final HTTPDecoder<byte[], NVGenericMap> BytesToNVGM = new HTTPDecoder<byte[], NVGenericMap>(){

    public NVGenericMap decode(byte[] input)
    {

      return GSONUtil.fromJSONGenericMap(SharedStringUtil.toString(input), null, Base64Type.DEFAULT);

    }
  };

  public static final HTTPDecoder<HTTPResponseData, NVGenericMap> HRDToNVGM = new HTTPDecoder<HTTPResponseData, NVGenericMap>(){

    public NVGenericMap decode(HTTPResponseData input)
    {
        return GSONUtil.fromJSONGenericMap(SharedStringUtil.toString(input.getData()), null, Base64Type.DEFAULT);
    }
  };

  public static final HTTPDecoder<HTTPResponseData, List<NVGenericMap>> HRDToNVGMList = new HTTPDecoder<HTTPResponseData, List<NVGenericMap>>(){

    public List<NVGenericMap> decode(HTTPResponseData input)
    {
      return GSONUtil.fromJSONGenericMapArray(SharedStringUtil.toString(input.getData()), Base64Type.DEFAULT);
    }
  };


}

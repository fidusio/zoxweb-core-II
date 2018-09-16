/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.shared.api;

import java.util.List;
import java.util.Set;

import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.util.DynamicEnumMap;
import org.zoxweb.shared.util.IDGenerator;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.data.LongSequence;
import org.zoxweb.shared.db.QueryMarker;

/**
 * The API data storage interface.
 * @author mzebib
 * @param <ST> 
 *
 */
public interface APIDataStore<ST>
	extends APIServiceProvider<ST>
{
	
	/**
	 * @return the data stpre name
	 */
	public String getStoreName();
	
	/**
	 * This method retrieves the storage tables.
	 * @return the set of tables
	 */
	public Set<String> getStoreTables();
	
	/**
	 * This method searches for documents.
	 * @param nvce
	 * @param fieldNames
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException 
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> search(NVConfigEntity nvce, List<String> fieldNames, QueryMarker ... queryCriteria) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	/**
	 * 
	 * Search based on the class name as collection
	 * 
	 * @param className
	 * @param fieldNames
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> search(String className, List<String> fieldNames, QueryMarker ... queryCriteria) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	/**
	 * Report search based on the NVConfigEntity collection type
	 * 
	 * @param nvce
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <T> APISearchResult<T> batchSearch(NVConfigEntity nvce, QueryMarker ... queryCriteria) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	/**
	 * Report serach based on hte class collection
	 * 
	 * @param className
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <T> APISearchResult<T> batchSearch(String className, QueryMarker ... queryCriteria) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	/**
	 * Batch result retrieval
	 * 
	 * @param results
	 * @param startIndex
	 * @param batchSize
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <T, V extends NVEntity> APIBatchResult<V> nextBatch(APISearchResult<T> results, int startIndex, int batchSize) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	/**
	 * User the specific search
	 * 
	 * @param userID
	 * @param nvce
	 * @param fieldNames
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> userSearch(String userID, NVConfigEntity nvce, List<String> fieldNames, QueryMarker ... queryCriteria) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	/**
	 * 
	 * User the specific search
	 * 
	 * @param userID
	 * @param className
	 * @param fieldNames
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> userSearch(String userID, String className, List<String> fieldNames, QueryMarker ... queryCriteria) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	/**
	 * This method searches for documents based on id.
	 * @param nvce
	 * @param ids
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException 
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> searchByID(NVConfigEntity nvce, String... ids) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	
	/**
	 * This method searches for documents based on id.
	 * @param className the class must extend NVEntity otherwise it will APIException
	 * @param ids
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException 
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> searchByID(String className, String... ids) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	
	/**
	 * This method searches for documents based on id.
	 * @param userID 
	 * @param nvce
	 * @param ids
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException 
	 * @throws APIException
	 */
	public <V extends NVEntity> List<V> userSearchByID(String userID, NVConfigEntity nvce, String... ids) 
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	/**
	 * This method inserts a document.
	 * @param nve
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException 
	 * @throws APIException
	 */
	public <V extends NVEntity> V insert(V nve)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	/**
	 * This method deletes a document.
	 * @param nve
	 * @param withReference
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException 
	 * @throws APIException
	 */
	public <V extends NVEntity> boolean delete(V nve, boolean withReference)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	
	/**
	 * This method will delete document that matches the query criteria
	 * 
	 * @param nvce
	 * @param queryCriteria
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessException
	 * @throws APIException
	 */
	public <V extends NVEntity> boolean delete(NVConfigEntity nvce, QueryMarker ... queryCriteria)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	/**
	 * This method updates a document.
	 * @param nve
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public <V extends NVEntity> V update(V nve) 
			throws NullPointerException, IllegalArgumentException, APIException;
	
	/**
	 * 
	 * This method patch a document.
	 * 
	 * @param nve to be updated
	 * @param updateTS 
	 * @param sync if true the datastore update become synchronized
	 * @param updateRefOnly will update the reference only
	 * @param includeParam if true the nvConfigNames list will be updated, if false the nvConfigNames will be excluded
	 * @param nvConfigNames to be updated if null or empty the whole object will be updated
	 * @return the matching entities
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public <V extends NVEntity> V patch(V nve, boolean updateTS, boolean sync, boolean updateRefOnly, boolean includeParam, String... nvConfigNames) 
			throws NullPointerException, IllegalArgumentException, APIException;
	
	/**
	 * This method counts the number of matched documents found.
	 * @param nvce
	 * @param queryCriteria
	 * @return the matching cpunt
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public long countMatch(NVConfigEntity nvce, QueryMarker ... queryCriteria)
			throws NullPointerException, IllegalArgumentException, APIException;
	
	
	/**
	 * This method inserts the dynamic enum into the database.
	 * @param dynamicEnumMap
	 * @return the enum map
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public DynamicEnumMap insertDynamicEnumMap(DynamicEnumMap dynamicEnumMap)
			throws NullPointerException, IllegalArgumentException, APIException;
	
	/**
	 * This method updates the dynamic enum already in the database.
	 * @param dynamicEnumMap
	 * @return the updated enum map 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public DynamicEnumMap updateDynamicEnumMap(DynamicEnumMap dynamicEnumMap)
			throws NullPointerException, IllegalArgumentException, APIException;
	
	/**
	 * This method searches for the dynamic enum by name.
	 * @param name
	 * @return the matching enum map
	 * @throws NullPointerException 
	 * @throws IllegalArgumentException 
	 * @throws APIException 
	 */
	public DynamicEnumMap searchDynamicEnumMapByName(String name)
			throws NullPointerException, IllegalArgumentException, APIException;
	
	/**
	 * This method deletes a dynamic enum based on name.
	 * @param name
	 * @throws NullPointerException 
	 * @throws IllegalArgumentException 
	 * @throws APIException 
	 */
	public void deleteDynamicEnumMap(String name)
		throws NullPointerException, IllegalArgumentException, APIException;
	
	public <NID> IDGenerator<String, NID> getIDGenerator();
	
	
	/**
	 * This method returns a list of dynamic enum map in the dynamic enum map collection.
	 * @param domainID
	 * @param userID
	 * @return all the enum maps
	 * @throws NullPointerException 
	 * @throws IllegalArgumentException 
	 * @throws AccessException 
	 * @throws APIException 
	 */
	public List<DynamicEnumMap> getAllDynamicEnumMap(String domainID, String userID)
		throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	
	public <NT, RT> NT lookupByReferenceID(String metaTypeName, RT objectId);
	
	
	public <NT, RT, NIT> NT lookupByReferenceID(String metaTypeName, RT objectId, NIT projection);
	
	LongSequence createSequence(String sequenceName)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;;
	LongSequence createSequence(String sequenceName, long startValue, long defaultIncrement)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	void deleteSequence(String sequenceName)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	long currentSequenceValue(String sequenceName)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	long nextSequenceValue(String sequenceName)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	long nextSequenceValue(String sequenceName, long increment)
			throws NullPointerException, IllegalArgumentException, AccessException, APIException;
	
	boolean isValidReferenceID(String refID);
}

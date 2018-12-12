/**
 * This class implements the TTTDictionaryADT interface, which creates
 * a dictionary of game configurations and uses a hash table to 
 * search for and compare various configurations
 * @author Joshua Noble -- 250700795
 *
 */
public class TTTDictionary implements TTTDictionaryADT {
	
	private TTTLinkedRecord[] hashTable;
	private int count;
	private int size;
	
	/**
	 * Constructor which creates an empty dictionary according to the given size,
	 * initializing all entries to null
	 * @param size Size of the dictionary to create
	 */
	public TTTDictionary(int size) {
		hashTable = new TTTLinkedRecord[size];
		count = 0;
		this.size = size;
		
		for (int i = 0; i < size; i++) {
			hashTable[i] = null;
		}	
	}
	
	/**
	 * Adds a give record into the dictionary
	 * @param record The TTTRecord to add into the dictionary
	 * @return int Returns 0 if there was no collision, otherwise -1
	 * @throws DuplicatedKeyException Throws this exception if a give record already exists in the dictionary
	 */
	@Override
	public int put(TTTRecord record) throws DuplicatedKeyException {
		String config = record.getConfiguration();
		int hashValue = getHashValue(config);
		
		if (hashTable[hashValue] == null) { // if no record exists at calculated hashValue
			hashTable[hashValue] = new TTTLinkedRecord(record);
			count += 1;
			return 0; // no collision, return 0
		} else {
			TTTLinkedRecord currentRecord = hashTable[hashValue];
			
			// Loop through linked list, look for matching config
			while (currentRecord.getNext() != null && !currentRecord.getConfig().equals(config)) {
				currentRecord = currentRecord.getNext();
			}
			
			if (currentRecord.getConfig().equals(config)) { // if config already exists, throw exception
				throw new DuplicatedKeyException();
			} else {
				currentRecord.setNext(new TTTLinkedRecord(record)); // add config to end of linked list
				count += 1;
			}
			
			return 1; // collision, return 1
		}
	}

	/**
	 * Removes a given config from the dictionary
	 * @param config Config to be removed
	 * @throws InexistentKeyException The exception thrown if the given config doesn't exist in the dictionary
	 */
	@Override
	public void remove(String config) throws InexistentKeyException {
		int hashValue = getHashValue(config);
		
		if (hashTable[hashValue] != null) { // if a record exists at calculated hashValue
			TTTLinkedRecord prevRecord = null;
			TTTLinkedRecord currentRecord = hashTable[hashValue];
			
			// loop through linked list, look for matching config
			while (currentRecord != null && !currentRecord.getConfig().equals(config)) {
				prevRecord = currentRecord;
				currentRecord = currentRecord.getNext();
			}
			
			if (currentRecord != null && currentRecord.getConfig().equals(config)) {
				if (prevRecord == null) { // if there was only one record at this hashValue
					hashTable[hashValue] = currentRecord.getNext();
				} else { // if there were multiple records at this hashValue
					prevRecord.setNext(currentRecord.getNext());
				}
				
				count -= 1;
			} else { // if record isn't found, throw exception
				throw new InexistentKeyException();
			}
		} else { // if record isnt found, throw exception
			throw new InexistentKeyException();
		}
	}
	
	/**
	 * Returns the TTTRecord of a given config
	 * @param config Config to search for
	 * @return TTTRecord The TTTRecord of the given config
	 */
	@Override
	public TTTRecord get(String config) {
		int hashValue = getHashValue(config);
		
		if (hashTable[hashValue] == null) { // if no record exists at calculated hashValue
			return null;
		} else {
			TTTLinkedRecord currentRecord = hashTable[hashValue];
			
			// loop through linked list, look for matching config
			while (currentRecord != null && !currentRecord.getConfig().equals(config)) {
				currentRecord = currentRecord.getNext();
			}
			
			if (currentRecord == null) { // if record doesn't exist, return null
				return null;
			} else {
				return currentRecord.getRecord();
			}
		}
	}

	/**
	 * Returns the number of elements in the dictionary
	 * @return int Number of elements contained in the dictionary
	 */
	@Override
	public int numElements() {
		return count;
	}

	/**
	 * Calculates the hash value of a given config
	 * @param config Config to calculate hash value for
	 * @return int Hash value after hash function is applied
	 */
	public int getHashValue(String config) {
		int length = config.length();
		int temp = 0;
		
		for (int i = 0; i < length; i++) {
			int letter = config.charAt(i);
			temp = (temp * 31 + letter) % size; // Use mod size to prevent overflows
		}
		
		// Compression function according to MAD concepts
		int a = 5;
		int b = 7;
		int newHash = Math.abs(a * temp + b) % (size - 1);
		
		return newHash;
	}
}
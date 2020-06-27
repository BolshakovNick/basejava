/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;

    void clear() {
        for (Resume r : storage) {
            r = null;
        }
    }

    //save the new element after last element
    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
            }
        }
    }

    //get the element
    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume = storage[i];
                break;
            }
        }
        return resume;
    }

    //delete element and shift other elements one left
    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < size - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[this.size];
        for (int i = 0; i < this.size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    //not null element length
    int size() {
        int result = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                result = i;
                this.size = i;
                break;
            }
        }
        return result;
    }
}

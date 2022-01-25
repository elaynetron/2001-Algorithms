'''
Execution Instruction
1. Run main() function below (by default, it should run when this file is executed).
2. Key in full file name when prompted (e.g. chr2.unlocalized.scaf.fna)
3. Key in string query when prompted
4. Results will print out, along with time taken to run the algorithms with inputs given.
'''

import math
import timeit

'''
Brute Force Algorithm/Naive Algorithm
'''
def BruteForceAlgo(string_query, contents):
    #result list to store all occurrences of string query
    result = []
    
    #run through every character in contents
    for char_i in range(len(contents)-len(string_query)):

        wrong = False
        #run through every character in string_query regardless of whether it is wrong/right
        for str_i in range(len(string_query)):
            if contents[char_i + str_i] != string_query[str_i]:
                wrong = True

        #if the whole string matched the content (i.e. correct)
        if not wrong:
            result.append(char_i)
                
    return result


'''KMP Algorithm'''
def KMPAlgo(string_query, contents):
    
    #Function to create LPS (longest proper prefix and suffix) array
    def CreateLPS(string_query):
        strlen = len(string_query)
        lps_array = [0]*strlen
        #length of the previous longest prefix suffix
        prevlen = 0
        curr_i = 1 #lps for index 0 is always 0 so can skip index 0

        #loop to calculate lps values for curr_i = 1 to curr_i = strlen-1
        while curr_i < strlen:
            if string_query[curr_i] == string_query[prevlen]:
                prevlen += 1
                lps_array[curr_i] = prevlen
                curr_i += 1
            else:
                if prevlen != 0:
                    prevlen = lps_array[prevlen-1]
                else:
                    lps_array[curr_i] = 0
                    curr_i += 1

        return lps_array

    strlen = len(string_query)
    contlen = len(contents)
    lps_array = CreateLPS(string_query)
    str_i = cont_i = 0
    result = []

    while cont_i < contlen:
        if string_query[str_i] == contents[cont_i]:
            str_i += 1
            cont_i += 1

        if str_i == strlen:
            result.append(cont_i - str_i)
            str_i = lps_array[str_i-1]

        #after str_i matches, mismatch
        elif cont_i < contlen and string_query[str_i] != contents[cont_i]:
            if str_i != 0:
                str_i = lps_array[str_i-1]
            else:
                cont_i += 1
                
    return result


'''Rabin Karp'''
def RabinKarpAlgo(string_query, contents):
    num_chars = 256 #num of characters in input, in this case it is always 256
    #as we are using ord, and 256 is the total number of characters in ASCII
    prime_num = 101 #any prime number will work

    strlen = len(string_query)
    contlen = len(contents)
    curr_i = str_i = 0
    strhash = conthash = 0
    h = math.pow(num_chars, strlen-1) % prime_num #h = d^(strlen-1) %q
    result = []

    #Calculate hash value for string_query and first window of contents
    for curr_i in range(strlen):
        strhash = (num_chars * strhash + ord(string_query[curr_i])) %prime_num
        conthash = (num_chars * conthash + ord(contents[curr_i])) %prime_num

    #Repeat for subsequent windows of contents
    for curr_i in range(contlen-strlen+1):
        #Check if hash values of window and pattern match
        if strhash == conthash:
            #then check for each character
            for str_i in range(strlen):
                if contents[curr_i + str_i] != string_query[str_i]:
                    break

            str_i += 1
            if str_i == strlen:
                result.append(curr_i)

        #Calculate for next window, remove first digit's and add last digit's
        if curr_i < contlen-strlen:
            conthash = (num_chars * (conthash - ord(contents[curr_i])*h) + ord(contents[curr_i + strlen])) % prime_num

            #if we get negative values for conthash
            if conthash < 0:
                conthash += prime_num

    return result

def PerformSearch(filename, string_query):
    file = open (filename)
    contents = file.read()

    #process contents to remove first line in data file
    content_split = contents.split('\n')
    contents = ''.join(content_split[1:])
    #print(contents[:3000]) #to check file contents

    print("\nString Query was found at indexes (starting from 0):")
    #call various algorithms to perform search (all should produce same results)
    BFresult = BruteForceAlgo(string_query, contents)
    KMPresult = KMPAlgo(string_query, contents)
    RabinKarpresult = RabinKarpAlgo(string_query, contents)
    print("Brute Force Algorithm: " + str(BFresult))
    print("\nKMP Algorithm: " + str(KMPresult))
    print("\nRabin Karp Algorithm: " + str(RabinKarpresult))
    print("\nSame results for all algorithms? " + str(BFresult == KMPresult == RabinKarpresult))

def BFtime(filename, string_query): 
    SETUP_CODE = '''
from __main__ import BruteForceAlgo
file = open ("''' + filename + '''")
contents = file.read()

content_split = contents.split("\\n")
contents = "".join(content_split[1:])'''
  
    TEST_CODE = 'BruteForceAlgo("' + string_query + '", contents)'

    print("Time to execute BruteForceAlgo")
    print(timeit.timeit(setup = SETUP_CODE, 
                          stmt = TEST_CODE,
                          number = 1))

def KMPtime(filename, string_query): 
    SETUP_CODE = '''
from __main__ import KMPAlgo
file = open ("''' + filename + '''")
contents = file.read()

content_split = contents.split("\\n")
contents = "".join(content_split[1:])'''
  
    TEST_CODE = 'KMPAlgo("' + string_query + '", contents)'

    print("\nTime to execute KMPAlgo")
    print(timeit.timeit(setup = SETUP_CODE, 
                          stmt = TEST_CODE,
                          number = 1))

def RKtime(filename, string_query): 
    SETUP_CODE = '''
from __main__ import RabinKarpAlgo
file = open ("''' + filename + '''")
contents = file.read()

content_split = contents.split("\\n")
contents = "".join(content_split[1:])'''
  
    TEST_CODE = 'RabinKarpAlgo("' + string_query + '", contents)'

    print("\nTime to execute RabinKarpAlgo")
    print(timeit.timeit(setup = SETUP_CODE, 
                          stmt = TEST_CODE,
                          number = 1))

def main():
    filename = input("Filename: ")
    string_query = input("String Query: ")
    PerformSearch(filename, string_query)
    print('---------------------------------------------------------')
    BFtime(filename, string_query)
    KMPtime(filename, string_query)
    RKtime(filename, string_query)

main()

#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <bitset> 


#include "key.hpp"
#include "symbol.hpp"
#include "math.h"
#include <unordered_map>
#include "math.h"


std::string me;
std::string encrypted;
std::string table_filename;
bool verbose = false;

Symbol::Symbol(const std::string& filename) {
	T.resize(N);
	std::string buffer;
    std::fstream input(filename.c_str(), std::ios::in);
    for (int i = 0; i < N; i++) {
        std::getline(input, buffer);
        T[i].set_string(buffer);
    }
    input.close();
	
	// insert your code here???
}

//update hash so it works with a string (override to string instead of Key object)
namespace std {
	template <> struct hash<Key>{
		std::size_t operator() (const Key& k) const
		{
			using std::size_t;
			using std::hash;
			using std::string; 
			return hash<string>()(k.get_string()); //hash using key.string 
		}
	};
}

void Symbol::decrypt(const std::string& encrypted){
	//supposed to be faster
	
	Key encr(encrypted); //make a key
	Key curr; 
	Key increment; 

	unsigned long long int limit = pow(32, C/2); //set bounds 
	//divide sample subset by two
	int partition = C/2;
	int other_part = C - partition; 

	Key part = curr; //maintain curr value for later
	std::string str = curr.get_string();
	str[str.length()-1] = 'b'; //create increment 
	increment.set_string(str);

	std::unordered_map<Key, Key> map; //create hash table



	for(unsigned long long int i = 0; i < limit; i++){
		
		map[part.subset_sum(T,verbose)] = part; //hash
		part+=increment;//increment to next password half
	
		
	}
	std::vector<Key> decode;  //array
	//new limit
	limit = pow(32, other_part);
	part = curr;  //reset part to "aaaaaa..."

	str = curr.get_string();
	str[other_part-1] = 'b'; //change to other increment 
	increment.set_string(str);

	for(unsigned long long int i = 0; i < limit; i++){
		Key comp = encr;
		comp -= part.subset_sum(T,verbose);
		std::unordered_map<Key, Key>::const_iterator it = map.find(comp);
		if(it != map.end()){
			Key decoded = part;
			decoded += it->second; 
			decode.push_back(decoded);
		}
		part+=increment;
	}
	
	map.clear();
	decode.shrink_to_fit();
	
	for(unsigned int i = 0; i < decode.size(); i++){
		decode[i].show_word();
	}

}

void usage(const std::string& error_msg="") {
	if (!error_msg.empty()) std::cout << "ERROR: " << error_msg << '\n';
	std::cout << me << ": Symbol table-based cracking of Subset-sum password"
		<< " with " << B << " bits precision\n"
	    << "USAGE: " << me << " <encrypted> <table file> [options]\n"
		<< "\nArguments:\n"
		<< " <encrypted>:   encrypted password to crack\n"
		<< " <table file>:  name of file containing the table to use\n"
		<< "\nOptions:\n"
		<< " -h|--help:     print this message\n"
		<< " -v|--verbose:  select verbose mode\n\n";
	exit(0);
}

void initialize(int argc, char* argv[]) {
	me = argv[0];
	if (argc < 3) usage("Missing arguments");
	encrypted = argv[1];
	table_filename = argv[2];
	for (int i=3; i<argc; ++i) {
		std::string arg = argv[i];
		if (arg == "-h" || arg == "--help") usage();
		else if (arg == "-v" || arg == "--verbose") verbose = true;
		else usage("Unrecognized argument: " + arg);
	}
}


int main(int argc, char *argv[]){
	
	initialize(argc, argv);
	Symbol a(table_filename);
	//clock_t start = clock();
	// insert your code here
	a.decrypt(encrypted);
	//clock_t end = clock();

	//double elapsed = double(end-start) / CLOCKS_PER_SEC;
	//std::cout<< elapsed << '\n';
	return 0;
}

My implementation of the Booyer Moore Hoorspool algorithm is 
very similar to the one implementation in the lecture notes. 
My implementation of enabling wildcards is basically saying that
wildcards are every letter and therefor the bad character shift 
implementation of every character needs to be similar to the bad
shift implementation of normal characters in the needle. 

The bad shift for normal characters is like this:

for (int i = 0; i < last ; i++) {
	badShift[needle[i]] = last - i;
}

So for all the other values I did this. 

for (int i = 0; i < badShift.length; i++ ) {
	badShift[i] = needle.length - wcc;
}   
Where wcc is wild card count. 

To add the wildcards i did three things. 
1.Count the wildcards in the needle.
2.Subtract the wildcards from the bad character shift. 
3.While checking for matches, also return true if the character matches
the wildcard.



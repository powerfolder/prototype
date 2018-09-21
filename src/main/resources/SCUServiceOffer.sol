pragma solidity ^0.4.24;

// ----------------------------------------------------------------------------
// 'SCU' 'Space.Cloud.Unit.Test v2 Token' token contract
//
// Symbol      : SCT
// Name        : Space.Cloud.Unit.Test.v2
// Total supply: 150,000,000.000000000000000000
// Decimals    : 18
//
// (c) openzepplin / Smart Contract Solutions, Inc 2016. The MIT Licence.
// (c) Max / SCU GmbH 2018. The MIT Licence.
// ----------------------------------------------------------------------------


import "./Ownable.sol";

contract SCUServiceOffer is Ownable {

	string public ID;
	string public serviceProvider;
	uint256 public pricePerMonth;
	string public description;
	string public location;
	
	constructor(string _ID, string _serviceProvider, uint256 _pricePerMonth, string _description, string _location) public {
		owner = msg.sender;
		ID=_ID;
		serviceProvider=_serviceProvider;
		pricePerMonth=_pricePerMonth;
		description=_description;
		location=_location;
	}

}

contract SCUMarketplace is Ownable {

	address[] activeOfferArray;

    event SCUServiceOfferCreated(SCUServiceOffer serviceOffer);

	constructor() public {
		owner=msg.sender;
	}

	function createServiceOffer(string _ID, string _serviceProvider, uint256 _pricePerMonth, string _description, string _location) public returns (address) {
		SCUServiceOffer serviceOffer = new SCUServiceOffer(_ID, _serviceProvider, _pricePerMonth, _description, _location);
		activeOfferArray.push(address(serviceOffer));
		emit SCUServiceOfferCreated(serviceOffer);
		return address(serviceOffer);	 
	}

}
